package com.jnda.network.provider

import com.jnda.network.data.model.ServiceResponse
import com.jnda.network.data.model.ServiceResult
import com.jnda.network.di.NetworkComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class NetworkProviderImpl(
    @Named(NetworkComponent.DISPATCHER_IO) val ioDispatcher: CoroutineDispatcher,
) : NetworkProvider {
    override suspend fun <T> request(service: suspend () -> ServiceResponse<T>): ServiceResult<T> {
        return withContext(ioDispatcher) {
            try {
                service.invoke().let { response ->
                    when (response.code) {
                        200 -> {
                            ServiceResult.Success(
                                code = response.code,
                                data = response.data,
                                message = "success"
                            )
                        }
                        else -> {
                            ServiceResult.Failed(
                                code = response.code ?: -1,
                                data = response.data,
                                message = response.message ?: ""
                            )
                        }
                    }
                }
            } catch (ex: Exception) {
                ServiceResult.Failed(
                    code = -1,
                    data = null,
                    message = "Failed"
                )
            }
        }
    }

    override suspend fun <T> requestDefault(service: suspend () -> T): ServiceResult<T> {
        return withContext(ioDispatcher) {
            try {
                service.invoke().let { response ->
                    ServiceResult.Success(0, response, "")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                ServiceResult.Failed(0, null, "")
            }
        }
    }

}