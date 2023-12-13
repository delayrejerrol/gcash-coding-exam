package com.jnda.signup.data.repository

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
class SignUpRepositoryImplTest {

    @Mock
    private lateinit var userDAO: UserDAO
    private lateinit var mockSignUpRepository: SignUpRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockSignUpRepository = SignUpRepositoryImpl(
            userDAO = userDAO,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `getting a success result when the user is successfully added`() {
        runBlocking {
            val mockEntity = UserEntity(
                0, "mock_username", "mock_password"
            )
            whenever(userDAO.insert(mockEntity)).thenReturn(1)
            mockSignUpRepository.insertUser("mock_username", "mock_password").apply {
                assertEquals(1, this)
            }
        }
    }
}