//package org.example.expert.domain.user.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//import net.datafaker.Faker;
//import org.example.expert.domain.user.dto.response.UserResponse;
//import org.example.expert.domain.user.entity.User;
//import org.example.expert.domain.user.enums.UserRole;
//import org.example.expert.domain.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@SpringBootTest
//public class AllUserGetTest {
//
//  @Autowired
//  UserService userService;
//
//  @Autowired
//  JdbcTemplate jdbcTemplate;
//
//  private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
//
// @Test
//  public void sss() {
//    long beforeTime = System.currentTimeMillis();
//
//    //given
//    final int BATCH_SIZE = 10_000;
//    List<User> userList = new ArrayList<>();
//    int page = 1;
//    int size = 5;
//    String username = "jane";
//
//    String sql = "INSERT INTO users (email, password, username, user_role) " +
//        "VALUES (?, ?, ?, ?)";
//
//    userList.add(new User("ppp@naver.com","1234","jane",UserRole.USER));
//
//    //save All -> 1,000개 -> 1488ms
//    //jdbc batch insert -> 375ms
//    // 10,000 -> 733ms
//    // 100,000 -> 2674ms
//    // 1,000,000 -> 21794ms, 조회 시간 764ms
//    //
//    Faker faker = new Faker();
//    for(int i = 1; i<1_000;i++){
//      String fullName = faker.name().firstName();
//      String email = fullName.toLowerCase()+ i + "@gmail.com";
//      String password = "1234";
//      UserRole userRole = UserRole.valueOf("USER");
//      userList.add(new User(email,password,fullName,userRole));
//    }
//   long afterTime = System.currentTimeMillis();
//   long secDiffTime = (afterTime - beforeTime);
//   log.info("데이터 만드는데에 걸린 시간 : " + secDiffTime + "ms");
//
//    jdbcTemplate.batchUpdate(sql,
//        userList,
//        BATCH_SIZE,
//        (PreparedStatement ps, User user) -> {
//      ps.setString(1, user.getEmail());
//      ps.setString(2, user.getPassword());
//      ps.setString(3,user.getUsername());
//      ps.setString(4, user.getUserRole().toString());
//        });
//
//   long afterTime2 = System.currentTimeMillis();
//   long secDiffTime2 = (afterTime2 - afterTime);
//    log.info("배치에 걸린 시간 : " + secDiffTime2 + "ms");
//
//    //when
//    Page<UserResponse> responses = userService.getAllUser(page,size,username);
//
//    //then
//    assertThat(responses).isNotNull();
//    assertThat(responses.getContent()).isNotEmpty();
//    assertThat(responses.getContent().size()).isLessThanOrEqualTo(size);
//    assertThat(responses.getTotalElements()).isGreaterThanOrEqualTo(1);
//
//    for (UserResponse response : responses.getContent()) {
//      assertThat(response.getUsername()).contains(username);
//    }
//
//    long afterTime3 = System.currentTimeMillis();
//    long secDiffTime3 = (afterTime3-afterTime2);
//    log.info(secDiffTime3 + "ms");
//  }
//
//}
