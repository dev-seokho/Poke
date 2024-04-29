package com.noah.sns.poke.business.user.interfaces.response

import com.noah.sns.poke.business.user.domain.entity.User
import com.noah.sns.poke.global.auth.TokenInfo

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

data class SignInResponse(
    val grantType: String,
    val accessToken: String,
) {
    companion object {
        fun of(tokenInfo: TokenInfo) : SignInResponse {
            return SignInResponse(
                grantType = tokenInfo.grantType,
                accessToken = tokenInfo.accessToken
            )
        }
    }
}