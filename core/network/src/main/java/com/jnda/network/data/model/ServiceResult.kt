package com.jnda.network.data.model

import androidx.annotation.Keep

@Keep
sealed class ServiceResult<T>(
    val code: Int? = -1,
    val data: T? = null,
    val message: String = "",
) {
    class Success<T>(code: Int, data: T?, message: String) : ServiceResult<T>(code, data, message)
    class Failed<T>(code: Int, data: T?, message: String) : ServiceResult<T>(code, data, message)
}
