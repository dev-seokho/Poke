package com.noah.sns.poke.business.user.interfaces.request

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.global.support.enums.Gender
import java.time.LocalDate

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: LocalDate,
    val gender: Gender
) {
    companion object {
        fun toEntity(signUpRequest: SignUpRequest): User {
            return User(
                name = signUpRequest.name,
                email = signUpRequest.email,
                password = signUpRequest.password,
                birthDate = signUpRequest.birthDate,
                gender = signUpRequest.gender
            )
        }
    }
}