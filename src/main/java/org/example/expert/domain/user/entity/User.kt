package org.example.expert.domain.user.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.user.enums.UserRole

@Entity
@NoArgsConstructor
@Table(
    name = "users", indexes = [Index(name = "idx_user_name", columnList = "username")]
)
class User(
) : Timestamped() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true)
    var email: String? = null

    var password: String? = null

    var username: String? = null

    @Enumerated(EnumType.STRING)
    var userRole: UserRole? = null

    var imagePath: String? = null

    constructor(id: Long, email: String, username: String, userRole: UserRole) : this() {
        this.id = id
        this.email = email
        this.username = username
        this.userRole = userRole
    }

    constructor(
        email: String,
        password: String,
        username: String,
        userRole: UserRole
    ) : this() {
        this.email = email
        this.password = password
        this.username = username
        this.userRole = userRole
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun updateRole(userRole: UserRole) {
        this.userRole = userRole
    }

    companion object {
        @JvmStatic
        fun fromAuthUser(authUser: AuthUser): User {
            return User(authUser.id, authUser.email, authUser.username, authUser.userRole)
        }
    }


}