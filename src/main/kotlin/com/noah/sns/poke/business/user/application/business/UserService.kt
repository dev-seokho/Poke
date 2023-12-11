package com.noah.sns.poke.business.user.application.business

import com.noah.sns.poke.business.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
}