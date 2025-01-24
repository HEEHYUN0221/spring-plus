//package org.example.expert;
//
//import jakarta.annotation.PostConstruct;
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import net.datafaker.Faker;
//import org.example.expert.domain.user.entity.User;
//import org.example.expert.domain.user.enums.UserRole;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class InitData {
//
//  private final JdbcTemplate jdbcTemplate;
//
//  @PostConstruct
//  @Transactional
//  public void init() {
//    final int BATCH_SIZE = 10_000;
//    List<User> userList = new ArrayList<>();
//
//    String sql = "INSERT INTO users (email, password, username, user_role) " +
//        "VALUES (?, ?, ?, ?)";
//
//    Faker faker = new Faker();
//    for(int i = 0; i<1_000_000;i++){
//      String fullName = faker.name().firstName();
//      String email = fullName.toLowerCase()+ i + "@gmail.com";
//      String password = "1234";
//      UserRole userRole = UserRole.valueOf("USER");
//      userList.add(new User(email,password,fullName,userRole));
//    }
//
//    jdbcTemplate.batchUpdate(sql,
//        userList,
//        BATCH_SIZE,
//        (PreparedStatement ps, User user) -> {
//          ps.setString(1, user.getEmail());
//          ps.setString(2, user.getPassword());
//          ps.setString(3,user.getUsername());
//          ps.setString(4, user.getUserRole().toString());
//        });
//
//  }
//
//}
