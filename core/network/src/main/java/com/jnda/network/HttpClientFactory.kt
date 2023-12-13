package com.jnda.network

import okhttp3.OkHttpClient
import org.koin.core.annotation.Single

@Single
internal class HttpClientFactory {
    private val httpClient by lazy { OkHttpClient() }
    fun create(): OkHttpClient.Builder = httpClient.newBuilder()
}