package com.jnda.network.di

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import kotlin.test.assertNotNull

class NetworkComponentTest : KoinTest {
    @Test
    fun `should inject network component`() {
        val module = startKoin { modules(NetworkComponent().module) }
        assertNotNull(module)
    }
}