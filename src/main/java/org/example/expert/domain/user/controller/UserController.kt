package org.example.expert.domain.user.controller

import lombok.RequiredArgsConstructor
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page

@RestController
@RequiredArgsConstructor
class UserController (
    private val userService: UserService
) {

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId:Long):ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUser(userId))
    }

    @PutMapping("/users")
    fun changePassword(@AuthenticationPrincipal authUser: AuthUser,
                       @RequestBody userChangePasswordRequest: UserChangePasswordRequest)
    {
        userService.changePassword(authUser.id,userChangePasswordRequest)
    }

    @GetMapping("/users/all")
    fun getAllUser(@RequestParam(defaultValue = "1") page:Int,
                   @RequestParam(defaultValue = "10") size:Int,
                   @RequestParam(required = false) username:String
                   ): ResponseEntity<Page<UserResponse?>?> {
        return ResponseEntity.ok(userService.getAllUser(page,size, username))
    }

//    @GetMapping("/users/all")
//    fun getAllUser(@RequestParam(required = false) lastUserId:Long,
//                   @RequestParam(defaultValue = "10") size:Int,
//                   @RequestParam(required = false) username: String
//    ): ResponseEntity<List<UserResponse?>?> {
//        return ResponseEntity.ok(userService.getAllUser(lastUserId,size,username))
//    }

}