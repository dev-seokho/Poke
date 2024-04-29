package com.noah.sns.poke.business.user.application.business

import com.noah.sns.poke.business.user.domain.entity.UserRole
import com.noah.sns.poke.business.user.domain.repository.UserRepository
import com.noah.sns.poke.business.user.domain.repository.UserRoleRepository
import com.noah.sns.poke.business.user.interfaces.request.SignInRequest
import com.noah.sns.poke.business.user.interfaces.request.SignUpRequest
import com.noah.sns.poke.business.user.interfaces.response.SignInResponse
import com.noah.sns.poke.business.user.interfaces.response.SignUpResponse
import com.noah.sns.poke.global.auth.JwtTokenProvider
import com.noah.sns.poke.global.support.exception.MessageKey
import com.noah.sns.poke.global.support.enum.ROLE
import com.noah.sns.poke.global.support.exception.MethodArgumentInvalidException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        userRepository.findByEmail(signUpRequest.email)?.let {
            throw MethodArgumentInvalidException(MessageKey.ALREADY_EXIST_EMAIL)
        }

        val userEntity = SignUpRequest.toEntity(signUpRequest)
        val user = userRepository.save(userEntity)

        val userRoleEntity = UserRole(role = ROLE.MEMBER, user = user)
        userRoleRepository.save(userRoleEntity)

        return SignUpResponse.of(user)
    }

    @Transactional
    fun signIn(signInRequest: SignInRequest): SignInResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.email, signInRequest.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val tokenInfo = jwtTokenProvider.createToken(authentication)

        return SignInResponse.of(tokenInfo)
    }
}