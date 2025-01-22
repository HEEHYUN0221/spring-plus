package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.service.UserImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/images")
public class UserImageController {

  private final UserImageService userImageService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
      @AuthenticationPrincipal AuthUser authUser) {
    return new ResponseEntity<>(userImageService.saveImage(file,authUser), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<String> getProfileImage(@AuthenticationPrincipal AuthUser authUser) {
    return ResponseEntity.ok(userImageService.getProfileImage(authUser));
  }

  @DeleteMapping("/deletion")
  public ResponseEntity<String> deleteFile(@AuthenticationPrincipal AuthUser authUser) {
    return new ResponseEntity<>(userImageService.deleteImage(authUser), HttpStatus.OK);
  }
}
