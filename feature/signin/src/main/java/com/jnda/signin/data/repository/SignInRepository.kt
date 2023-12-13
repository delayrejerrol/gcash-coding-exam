package com.jnda.signin.data.repository

import com.jnda.storage.data.entity.UserEntity

interface SignInRepository {
    fun findByAccount(username: String, password: String): UserEntity?
}