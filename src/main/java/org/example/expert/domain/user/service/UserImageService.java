package org.example.expert.domain.user.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserImageService {

  private final UserRepository userRepository;
  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public String saveImage(MultipartFile file, AuthUser authUser)  {
    try {
      String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
      if(!Objects.equals(extension, "jpg") && !Objects.equals(extension, "png")){
        throw new InvalidRequestException("jpg와 png 파일만 업로드 가능합니다.");
      }

      User user = userRepository.findById(authUser.getId())
          .orElseThrow(() -> new InvalidRequestException("User not found"));
      if(user.getImagePath()!=null){
        amazonS3Client.deleteObject(bucket,user.getImagePath());
      }
      String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
      String folderName = "user/" + authUser.getId() + "/";
      String objectKey = folderName + uniqueFileName;

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(file.getContentType());// get image -> presigned URL
      metadata.setContentLength(file.getSize());// 폴더만들기   bucket/user/{userId}/{imageFilename}
      amazonS3Client.putObject(bucket, objectKey, file.getInputStream(), metadata);
      user.setImagePath(objectKey);
      userRepository.save(user);

      //presigned URL로 반환
      //1. 한사람에 하나만 프로필 이미지를 저장하게 할건지
      //2. 한사람에 여러개 프로필 이미지를 저장하고 메인 프로필 이미지를 저장하게 할건지(이건 URL을 리스트화하고,
      //   Map이나 Set으로 저장해서 순서도 수정할 수 있게하고 그러면 좋을거같은데 이건 좀 어려울듯.
      //3. 왜 POST 할때 뱉는 presignedURL과 GET 할때 뱉는 presignedURL이 달라야하는걸까?
      // 메소드 이름 최적화하고 V
      // 1번 방법으로 할려면 삭제가 동작해야하니까 삭제쪽 건드려야함. V > 성공
      // 그 후에 S3 구성한거랑 동작방식 TIL 정리.

      return "이미지 저장 성공";
    } catch (Exception e) {
      throw new RuntimeException("이미지 저장 실패");
    }
  }

  public String getProfileImage(AuthUser authUser) {
    User user = userRepository.findById(authUser.getId())
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    if(user.getImagePath()==null) {
      throw new InvalidRequestException("등록한 프로필 이미지가 없습니다.");
    }

    return generateGETPresignedUrl(user.getImagePath(), getPreSignedUrlExpiration());
  }

  @Transactional
  public String deleteImage(AuthUser authUser) {
    User user = userRepository.findById(authUser.getId())
        .orElseThrow(() -> new InvalidRequestException("User not found"));
    if(user.getImagePath()==null) {
      throw new InvalidRequestException("Image not found");
    }
    user.setImagePath(null);
    amazonS3Client.deleteObject(bucket,user.getImagePath());// 폴더 안에 모든걸 삭제할 수 있나?
    return "삭제 성공";
  }

  private String generateGETPresignedUrl(String key, Date expiration) {
    GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, key)
        .withMethod(HttpMethod.GET)
        .withExpiration(expiration);

    URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    return url.toString();
  }

  private Date getPreSignedUrlExpiration() {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 ; //1시간
    expiration.setTime(expTimeMillis);
    return expiration;
  }
}

