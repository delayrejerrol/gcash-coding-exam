package com.jnda.signup.di

import android.content.Context
import com.jnda.network.di.NetworkComponent
import com.jnda.signup.data.repository.SignUpRepository
import com.jnda.signup.viewmodel.SignUpViewModel
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

class SignUpComponentTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(mock { Context::class.java })
        modules(
            NetworkComponent().module,
            StorageComponent().module,
            SignUpComponent().module
        )
    }

    private val signUpRepository: SignUpRepository by inject()
    private val signUpViewModel: SignUpViewModel by inject()

    @Test
    fun `verifying when repository is injected`() {
        assertNotNull(signUpRepository)
    }

    @Test
    fun `verifying when viewmodel is injected`() {
        assertNotNull(signUpViewModel)
    }
}