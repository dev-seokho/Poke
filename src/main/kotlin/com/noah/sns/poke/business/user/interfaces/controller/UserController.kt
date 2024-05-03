package com.noah.sns.poke.business.user.interfaces.controller

import com.noah.sns.poke.business.user.application.business.CustomUserDetailsService
import com.noah.sns.poke.business.user.application.business.UserService
import com.noah.sns.poke.business.user.interfaces.request.SignInRequest
import com.noah.sns.poke.business.user.interfaces.request.SignUpRequest
import com.noah.sns.poke.business.user.interfaces.request.UpdateUserRequest
import com.noah.sns.poke.business.user.interfaces.response.SignInResponse
import com.noah.sns.poke.business.user.interfaces.response.SignUpResponse
import com.noah.sns.poke.business.user.interfaces.response.SearchMyInfoResponse
import com.noah.sns.poke.business.user.interfaces.response.UpdateUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val customUserDetailsService: CustomUserDetailsService
) {
    @PostMapping("/signup")
    fun singUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<SignUpResponse> {
        request.validate()
        return ResponseEntity.ok(userService.signUp(request))
    }

    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<SignInResponse> {
        request.validate()
        return ResponseEntity.ok(userService.signIn(request))
    }

    @GetMapping("/me")
    fun searchMyInfo(): ResponseEntity<SearchMyInfoResponse> {
        val userId = customUserDetailsService.getUserId()
        return ResponseEntity.ok(userService.searchMyInfo(userId))
    }

    @PatchMapping
    fun updateUser(
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UpdateUserResponse> {
        request.validate()
        val userId = customUserDetailsService.getUserId()
        return ResponseEntity.ok(userService.updateUser(request, userId))
    }
}