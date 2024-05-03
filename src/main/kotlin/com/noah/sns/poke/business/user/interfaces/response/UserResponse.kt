package com.noah.sns.poke.business.user.interfaces.response

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.global.support.enum.Gender
import java.time.LocalDate

data class SearchMyInfoResponse(
    val userId: Long,
    val email: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
) {
    companion object {
        fun of(user: User): SearchMyInfoResponse {
            return SearchMyInfoResponse(
                userId = user.id!!,
                email = user.email,
                name = user.name,
                birthDate = user.birthDate,
                gender = user.gender
            )
        }
    }
}

data class UpdateUserResponse(
    val userId: Long,
    val email: String,
    val name: String
) {
    companion object {
        fun of(user: User): UpdateUserResponse {
            return UpdateUserResponse(
                userId = user.id!!,
                email = user.email,
                name = user.name,
            )
        }
    }
}