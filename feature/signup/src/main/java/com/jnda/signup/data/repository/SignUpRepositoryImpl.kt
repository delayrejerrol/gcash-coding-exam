package com.jnda.signup.data.repository

import android.util.Log
import com.jnda.network.di.NetworkComponent
import com.jnda.storage.dao.UserDAO
import com.jnda.storage.data.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class SignUpRepositoryImpl(
    private val userDAO: UserDAO,
    @Named(NetworkComponent.DISPATCHER_IO) private val ioDispatcher: CoroutineDispatcher
) : SignUpRepository {
    override fun insertUser(username: String, password: String) : Long {
        var id: Long = 0
        runBlocking(ioDispatcher) {
            try {
                val userEntity = UserEntity(
                    username = username,
                    password = password
                )
                id = userDAO.insert(userEntity)
            } catch (_: Exception) { }
        }
        return id
    }
}