package com.noah.sns.poke.business.user.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?
) {
}