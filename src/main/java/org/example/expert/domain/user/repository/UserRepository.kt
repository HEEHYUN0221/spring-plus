package org.example.expert.domain.user.repository

import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository :JpaRepository<User, Long> {
    fun findByEmail(email: String?): Optional<User?>?

    fun existsByEmail(email: String?): Boolean

    @Query(
        "SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.username)"
                + " FROM User u WHERE (:username IS NULL OR u.username = :username)"
                + " ORDER BY u.id ASC"
    )
    fun findAllByUsername(
        pageable: Pageable?,
        username: String?
    ): Page<UserResponse?>?

//    @Query(
//        ("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.username)"
//                + " FROM User u WHERE (:email IS NULL OR u.email LIKE CONCAT('%','1','%')  )"
//                + " ORDER BY u.id ASC")
//    )
//    fun findAllByUsername(pageable: Pageable?, email: String?): Page<UserResponse?>?
//
//    @Query(
//        ("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email, u.username)"
//                + " FROM User u"
//                + " WHERE (:username IS NULL OR u.username = :username)"
//                + " AND (:lastUserId IS NULL OR u.id < :lastUserId)"
//                + " ORDER BY u.id ASC"
//                + " LIMIT :size")
//    )
//    fun findAllByUsername(lastUserId: Long?, size: Int, username: String?): List<UserResponse?>?

}