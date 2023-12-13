package com.jnda.network.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServiceResponse<K>(
    @SerializedName("code") val code: Int? = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: K?
)
