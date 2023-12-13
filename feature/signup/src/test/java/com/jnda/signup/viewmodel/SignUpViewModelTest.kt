package com.jnda.signup.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jnda.signup.data.repository.SignUpRepository
import com.jnda.signup.state.UserSignUpState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class SignUpViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockSignUpRepository: SignUpRepository
    private lateinit var mockSignUpViewModel: SignUpViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockSignUpViewModel = SignUpViewModel(mockSignUpRepository)
    }

    @Test
    fun `verifying user registration when the registration is success`() {
        runBlocking {
            whenever(mockSignUpRepository.insertUser("mock_username", "mock_password")).thenReturn(1)
            mockSignUpViewModel.addUser("mock_username", "mock_password")
            verify(mockSignUpViewModel.userSignUpState) {
                assertEquals(UserSignUpState.SUCCESS, this.mock.value)
            }
        }
    }

    @Test
    fun `verifying user registration when the registration is failed`() {
        runBlocking {
            whenever(mockSignUpRepository.insertUser("mock_username", "mock_password")).thenReturn(0)
            mockSignUpViewModel.addUser("mock_username", "mock_password")
            verify(mockSignUpViewModel.userSignUpState) {
                assertEquals(UserSignUpState.FAILED, this.mock.value)
            }
        }
    }
}