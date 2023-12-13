package com.jnda.signup.data.repository

interface SignUpRepository {
    fun insertUser(username: String, password: String) : Long
}