package com.noah.sns.poke.business.user.interfaces.response

import com.noah.sns.poke.business.user.domain.entity.User

data class SignUpResponse(
    val userId: Long
) {
    companion object {
        fun of(user: User) : SignUpResponse {
            return SignUpResponse(
                userId = user.id!!
            )
        }
    }
}