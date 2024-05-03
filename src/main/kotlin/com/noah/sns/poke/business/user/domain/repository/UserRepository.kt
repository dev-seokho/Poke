package com.noah.sns.poke.business.user.domain.repository

import com.noah.sns.poke.business.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

}