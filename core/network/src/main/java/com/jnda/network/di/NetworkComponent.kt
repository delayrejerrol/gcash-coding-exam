package com.jnda.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jnda.network.HttpClientFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@ComponentScan("com.jnda.network")
class NetworkComponent {
    companion object {
        const val DISPATCHER_IO = "ioDispatcher"
        const val DISPATCHER_MAIN = "mainDispatcher"
        const val DISPATCHER_DEFAULT = "defaultDispatcher"
    }

    @Single
    @Named(DISPATCHER_IO)
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Single
    @Named(DISPATCHER_MAIN)
    fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Single
    @Named(DISPATCHER_DEFAULT)
    fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Single
    internal fun provideOkHttpBuilder(
        httpClientFactory: HttpClientFactory
    ): OkHttpClient.Builder = httpClientFactory.create()

    @Single
    internal fun provideOkHttpClient(
        clientBuilder: OkHttpClient.Builder,
    ) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder
            .readTimeout(90L, TimeUnit.SECONDS)
            .connectTimeout(90L, TimeUnit.SECONDS)
            .writeTimeout(90L, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        return clientBuilder.build()
    }

    private fun provideGsonBuilder() : Gson =
        GsonBuilder().setLenient()
            .create()

    @Single
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(provideGsonBuilder()))
            .build()
    }
}