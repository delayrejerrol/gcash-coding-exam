package com.jnda.signin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jnda.signin.data.repository.SignInRepository
import com.jnda.signin.state.UserSignInState
import com.jnda.storage.data.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class SignInViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockSignInRepository: SignInRepository
    private lateinit var mockSignInViewModel: SignInViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockSignInViewModel = SignInViewModel(mockSignInRepository)
    }

    @Test
    fun `verifying when the user is successfully signed in`() {
        runBlocking {
            val mockResponse = UserEntity(
                userId = 1,
                username = "mock_username",
                password = "mock_password"
            )
            whenever(mockSignInRepository.findByAccount("mock_username", "mock_password")).thenReturn(mockResponse)
            mockSignInViewModel.signIn("mock_username", "mock_password")
            verify(mockSignInViewModel.userSignInState) {
                assertEquals(UserSignInState.SUCCESS, this.mock.value)
            }
        }
    }
}