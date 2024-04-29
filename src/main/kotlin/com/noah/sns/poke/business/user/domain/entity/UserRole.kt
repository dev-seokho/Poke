package com.noah.sns.poke.business.user.domain.entity

import com.noah.sns.poke.global.support.enum.ROLE
import jakarta.persistence.*

@Entity
@Table(name = "user_role")
class UserRole (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User
)