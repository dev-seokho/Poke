package com.noah.sns.poke.business.user.interfaces.response

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.global.support.enum.Gender
import java.time.LocalDate

data class UserInfoResponse(
    val id: Long,
    val email: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
) {
    companion object {
        fun of(user: User): UserInfoResponse {
            return UserInfoResponse(
                id = user.id!!,
                email = user.email,
                name = user.name,
                birthDate = user.birthDate,
                gender = user.gender
            )
        }
    }
}