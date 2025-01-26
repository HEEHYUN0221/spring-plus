package org.example.expert.domain.user.enums

import lombok.Getter
import lombok.RequiredArgsConstructor
import org.example.expert.domain.common.exception.InvalidRequestException
import java.util.*

@Getter
@RequiredArgsConstructor
enum class UserRole(role: String, description: String) {
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    USER("ROLE_USER", "사용자 권한");

    companion object {
        @JvmStatic
        fun of(role: String?): UserRole {
            return Arrays.stream(entries.toTypedArray())
                .filter { r: UserRole -> r.name.equals(role, ignoreCase = true) }
                .findFirst()
                .orElseThrow { InvalidRequestException("유효하지 않은 UerRole") }
        }
    }
}
