package com.noah.sns.poke.business.user.interfaces.controller

import com.noah.sns.poke.business.user.application.business.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
}