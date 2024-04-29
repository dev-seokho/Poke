package com.noah.sns.poke.business.user.application.business

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.business.user.domain.repository.UserRepository
import com.noah.sns.poke.global.auth.CustomUser
import com.noah.sns.poke.global.support.exception.MessageKey
import com.noah.sns.poke.global.support.exception.MessageSourceService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val messageSourceService: MessageSourceService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException(
            messageSourceService.getMessage(MessageKey.USER_NOT_FOUND)
            )

    private fun createUserDetails(user: User): UserDetails =
        CustomUser(
            user.id!!,
            user.email,
            passwordEncoder.encode(user.password),
            user.userRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )

    fun getUserId(): Long =
        (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
}