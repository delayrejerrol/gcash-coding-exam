package com.jnda.signin.data.repository

import com.jnda.network.di.NetworkComponent
import com.jnda.storage.dao.UserDAO
import com.jnda.storage.data.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class SignInRepositoryImpl(
    private val userDAO: UserDAO,
    @Named(NetworkComponent.DISPATCHER_IO) private val ioDispatcher: CoroutineDispatcher
) : SignInRepository {
    override fun findByAccount(username: String, password: String) : UserEntity? {
        var userEntity: UserEntity? = null
        runBlocking(ioDispatcher) {
            userEntity = userDAO.findByAccount(username, password)
        }
        return userEntity
    }
}