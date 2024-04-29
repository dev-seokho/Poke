package com.noah.sns.poke.business.user.interfaces.controller

import com.noah.sns.poke.business.user.application.business.UserService
import com.noah.sns.poke.business.user.interfaces.request.SignInRequest
import com.noah.sns.poke.business.user.interfaces.request.SignUpRequest
import com.noah.sns.poke.business.user.interfaces.response.SignInResponse
import com.noah.sns.poke.business.user.interfaces.response.SignUpResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
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
}