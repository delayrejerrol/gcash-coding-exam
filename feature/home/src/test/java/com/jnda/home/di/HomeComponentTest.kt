package com.jnda.home.di

import android.content.Context
import com.jnda.common.di.CommonComponent
import com.jnda.home.data.repository.WeatherRepository
import com.jnda.home.viewmodel.HomeViewModel
import com.jnda.home.viewmodel.LocationViewModel
import com.jnda.network.di.NetworkComponent
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

class HomeComponentTest: KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(mock { Context::class.java })
        modules(
            CommonComponent().module,
            NetworkComponent().module,
            StorageComponent().module,
            HomeComponent().module
        )
    }

    private val homeViewModel: HomeViewModel by inject()
    private val locationViewModel: LocationViewModel by inject()
    private val weatherRepository: WeatherRepository by inject()

    @Test
    fun `verifying viewmodels`() {
        assertNotNull(homeViewModel)
        assertNotNull(locationViewModel)
    }

    @Test
    fun `verifying repository`() {
        assertNotNull(weatherRepository)
    }
}