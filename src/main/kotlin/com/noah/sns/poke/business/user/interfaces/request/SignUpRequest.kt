package com.noah.sns.poke.business.user.interfaces.request

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.global.support.MessageKey
import com.noah.sns.poke.global.support.enums.Gender
import com.noah.sns.poke.global.support.exception.MethodArgumentInvalidException
import com.noah.sns.poke.global.support.util.isValidEmail
import com.noah.sns.poke.global.support.util.isValidPassword
import java.time.LocalDate

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: LocalDate,
    val gender: Gender
) {
    fun validate() {
        if (!this.email.isValidEmail()) {
            throw MethodArgumentInvalidException(MessageKey.INVALID_EMAIL_FORM)
        }

        if (this.name.length < 2 || this.name.length > 10 ) {
            throw MethodArgumentInvalidException(MessageKey.INVALID_NAME_LENGTH)
        }

        // 비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
        if (!this.password.isValidPassword()) {
            throw MethodArgumentInvalidException(MessageKey.INVALID_PASSWORD_FORM)
        }
    }
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