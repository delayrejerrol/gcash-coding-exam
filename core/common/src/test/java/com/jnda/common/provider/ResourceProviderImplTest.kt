package com.jnda.common.provider

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

class ResourceProviderImplTest {

    private lateinit var resourceProvider: ResourceProvider

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        resourceProvider = MockResourceProviderImpl()
    }

    @Test
    fun `should provide non empty string`() {
        resourceProvider.getString(0x1111).apply {
            assertTrue(this.isNotEmpty())
        }
    }
}

class MockResourceProviderImpl: ResourceProvider {
    override fun getString(resourceId: Int): String {
        return "non_empty_string"
    }

    override fun getString(resourceId: Int, vararg args: Any?): String {
        return if (args.isNotEmpty()) {
            var t = "test_string "
            args.forEach { arg ->
                t += arg
            }
            t
        } else {
            "test_string "
        }
    }


}