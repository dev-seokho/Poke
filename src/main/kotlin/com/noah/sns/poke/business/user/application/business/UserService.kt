package com.noah.sns.poke.business.user.application.business

import com.noah.sns.poke.business.user.domain.entity.UserRole
import com.noah.sns.poke.business.user.domain.repository.UserRepository
import com.noah.sns.poke.business.user.domain.repository.UserRoleRepository
import com.noah.sns.poke.business.user.interfaces.request.SignInRequest
import com.noah.sns.poke.business.user.interfaces.request.SignUpRequest
import com.noah.sns.poke.business.user.interfaces.request.UpdateUserRequest
import com.noah.sns.poke.business.user.interfaces.response.SignInResponse
import com.noah.sns.poke.business.user.interfaces.response.SignUpResponse
import com.noah.sns.poke.business.user.interfaces.response.SearchMyInfoResponse
import com.noah.sns.poke.business.user.interfaces.response.UpdateUserResponse
import com.noah.sns.poke.global.auth.JwtTokenProvider
import com.noah.sns.poke.global.support.exception.MessageKey
import com.noah.sns.poke.global.support.enum.ROLE
import com.noah.sns.poke.global.support.exception.EntityNotFoundException
import com.noah.sns.poke.global.support.exception.MethodArgumentInvalidException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signUp(
        signUpRequest: SignUpRequest
    ): SignUpResponse {
        userRepository.findByEmail(signUpRequest.email)?.let {
            throw MethodArgumentInvalidException(MessageKey.ALREADY_EXIST_EMAIL)
        }

        val userEntity = SignUpRequest.toEntity(signUpRequest, passwordEncoder.encode(signUpRequest.password))
        val user = userRepository.save(userEntity)

        val userRoleEntity = UserRole(role = ROLE.MEMBER, user = user)
        userRoleRepository.save(userRoleEntity)

        return SignUpResponse.of(user)
    }

    @Transactional
    fun signIn(
        signInRequest: SignInRequest
    ): SignInResponse {
        val user = userRepository.findByEmail(signInRequest.email)
            ?: throw EntityNotFoundException(MessageKey.USER_NOT_FOUND)

        if(!passwordEncoder.matches(signInRequest.password, user.password)) {
            throw RuntimeException("문제!!") // TODO: Auth 에러 밷기, 401 에러 처리들 싹 하기
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(user.email, user.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val tokenInfo = jwtTokenProvider.createToken(authentication)
        return SignInResponse.of(tokenInfo)
    }

    @Transactional(readOnly = true)
    fun searchMyInfo(
        userId: Long
    ): SearchMyInfoResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException(MessageKey.USER_NOT_FOUND)
        return SearchMyInfoResponse.of(user)
    }

    @Transactional
    fun updateUser(
        updateUserRequest: UpdateUserRequest, userId: Long
    ): UpdateUserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException(MessageKey.USER_NOT_FOUND)

        updateUserRequest.name?.let { user.name = it }
        updateUserRequest.password?.let { user.password = passwordEncoder.encode(it) }

        return UpdateUserResponse.of(user)
    }
}