package com.jnda.signin.di

import android.content.Context
import com.jnda.network.di.NetworkComponent
import com.jnda.signin.data.repository.SignInRepository
import com.jnda.signin.viewmodel.SignInViewModel
import com.jnda.storage.di.StorageComponent
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.kotlin.mock
import kotlin.test.assertNotNull

class SignInComponentTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(mock { Context::class.java })
        modules(
            NetworkComponent().module,
            StorageComponent().module,
            SignInComponent().module
        )
    }

    private val signInRepository: SignInRepository by inject()
    private val signInViewModel: SignInViewModel by inject()

    @Test
    fun `verifying when repository is injected`() {
        assertNotNull(signInRepository)
    }

    @Test
    fun `verifying when viewmodel is injected`() {
        assertNotNull(signInViewModel)
    }
}