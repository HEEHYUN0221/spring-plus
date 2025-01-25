package org.example.expert.domain.user.service

import lombok.RequiredArgsConstructor
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUser(userId: Long): UserResponse {
        val user = userRepository.findById(userId).orElseThrow {
            InvalidRequestException(
                "User not found"
            )
        }
        return UserResponse(user.id, user.email, user.username)
    }

    @Transactional
    fun changePassword(userId: Long, userChangePasswordRequest: UserChangePasswordRequest) {
        validateNewPassword(userChangePasswordRequest)

        val user = userRepository.findById(userId)
            .orElseThrow { InvalidRequestException("User not found") }

        if (passwordEncoder.matches(userChangePasswordRequest.newPassword, user.password)) {
            throw InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.")
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.oldPassword, user.password)) {
            throw InvalidRequestException("잘못된 비밀번호입니다.")
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.newPassword))
    }

    fun getAllUser(page: Int, size: Int, username: String?): Page<UserResponse?>? {
        val pageable: Pageable = PageRequest.of(page - 1, size)
        val users = userRepository.findAllByUsername(pageable, username)
        return users
    }

//    fun getAllUser(lastUserId: Long?, size: Int, username: String?): List<UserResponse?>? {
//        return userRepository.findAllByUsername(lastUserId, size, username)
//    }

    companion object {
        @JvmStatic
        private fun validateNewPassword(userChangePasswordRequest: UserChangePasswordRequest) {
            if (userChangePasswordRequest.newPassword.length < 8 ||
                !userChangePasswordRequest.newPassword.matches(".*\\d.*".toRegex()) ||
                !userChangePasswordRequest.newPassword.matches(".*[A-Z].*".toRegex())
            ) {
                throw InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
            }
        }
    }
}
