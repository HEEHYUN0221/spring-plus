package org.example.expert.domain.user.repository;

import java.util.Optional;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.username)"
      + " FROM User u WHERE (:username IS NULL OR u.username = :username)"
      + " ORDER BY u.id ASC")
  Page<UserResponse> findAllByUsername(Pageable pageable, String username);


  //nooffset 기반
//  @Query("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.username)"
//      + " FROM User u"
//      + " WHERE (:username IS NULL OR u.username = :username)"
//      + " AND (:lastUserId IS NULL OR u.id < :lastUserId)"
//      + " ORDER BY u.id ASC"
//      + " LIMIT :size")
//  List<UserResponse> findAllByUsername(Long lastUserId, int size, String username);

}
