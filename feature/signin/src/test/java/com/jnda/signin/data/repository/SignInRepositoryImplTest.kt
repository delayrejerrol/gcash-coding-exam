package com.jnda.signin.data.repository

import com.jnda.storage.dao.UserDAO
import com.jnda.storage.data.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SignInRepositoryImplTest {
    @Mock
    private lateinit var userDAO: UserDAO
    private lateinit var mockSignInRepository: SignInRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockSignInRepository = SignInRepositoryImpl(
            userDAO = userDAO,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `check if user exists`() {
        runBlocking {
            val mockResponse = UserEntity(
                userId = 1,
                username = "mock_username",
                password = "mock_password"
            )
            whenever(userDAO.findByAccount("mock_username", "mock_password")).thenReturn(mockResponse)
            mockSignInRepository.findByAccount("mock_username", "mock_password").apply {
                assertEquals("mock_username", this?.username)
            }
        }
    }
}