package com.noah.sns.poke.global.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.*

// Remember Token 을 적용하기 전까지 유효기간을 30분으로 설정
const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val log = LoggerFactory.getLogger(this.javaClass)!!
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     *  Token 생성
     */
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", (authentication.principal as CustomUser).userId)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key)
            .compact()

        return TokenInfo("Bearer", accessToken)

    }

    /**
     * Token 정보 추출
     */
    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰")
        val userId = claims["userId"] ?: throw RuntimeException("잘못된 토큰")

        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        val principal: UserDetails = CustomUser(userId.toString().toLong(), claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun getClaims(token: String): Claims =
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload

    /**
     * Token 검증
     */
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            log.error(e.message)
        }
        return false
    }
}