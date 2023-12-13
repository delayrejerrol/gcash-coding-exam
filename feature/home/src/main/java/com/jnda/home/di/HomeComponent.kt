package com.jnda.home.di

import com.jnda.home.data.WeatherService
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import retrofit2.Retrofit
import retrofit2.create

@Module
@ComponentScan("com.jnda.home")
class HomeComponent {

    @Factory
    fun provideWeatherService(retrofit: Retrofit) : WeatherService = retrofit.create()
}