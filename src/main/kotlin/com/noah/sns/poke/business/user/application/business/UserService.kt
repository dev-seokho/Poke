package com.noah.sns.poke.business.user.application.business

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.business.user.domain.repository.UserRepository
import com.noah.sns.poke.business.user.interfaces.request.SignUpRequest
import com.noah.sns.poke.business.user.interfaces.response.SignUpResponse
import com.noah.sns.poke.global.support.MessageKey
import com.noah.sns.poke.global.support.exception.MethodArgumentInvalidException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        userRepository.findByEmail(signUpRequest.email)?.let {
            throw MethodArgumentInvalidException(MessageKey.ALREADY_EXIST_EMAIL)
        }

        val user: User = SignUpRequest.toEntity(signUpRequest)
        return SignUpResponse.of(userRepository.save(user))
    }
}