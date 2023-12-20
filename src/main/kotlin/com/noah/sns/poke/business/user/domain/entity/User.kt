package com.noah.sns.poke.business.user.domain.entity

import com.noah.sns.poke.global.support.enums.Gender
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "email", unique = true, updatable = false)
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "birth_date")
    val birthDate: LocalDate,

    @Column(name = "gender")
    val gender: Gender,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null
)