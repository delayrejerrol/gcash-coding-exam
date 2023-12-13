package com.jnda.network.provider

import com.jnda.network.data.model.ServiceResponse
import com.jnda.network.data.model.ServiceResult

interface NetworkProvider {
    suspend fun <T> request(
        service: suspend () -> ServiceResponse<T>
    ) : ServiceResult<T>

    suspend fun <T> requestDefault(
        service: suspend () -> T
    ) : ServiceResult<T>
}