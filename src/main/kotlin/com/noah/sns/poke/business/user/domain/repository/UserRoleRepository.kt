package com.noah.sns.poke.business.user.domain.repository

import com.noah.sns.poke.business.user.domain.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRole, Long>