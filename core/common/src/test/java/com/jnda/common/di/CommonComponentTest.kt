package com.jnda.common.di

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import kotlin.test.assertNotNull

class CommonComponentTest: KoinTest {

    @Test
    fun `should inject common component`() {
        val module = startKoin { modules(CommonComponent().module) }
        assertNotNull(module)
    }
}