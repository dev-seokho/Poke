package com.noah.sns.poke.global.auth

data class TokenInfo (
    val grantType: String,
    val accessToken: String,
)