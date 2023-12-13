package com.jnda.common.provider

interface ResourceProvider {
    fun getString(resourceId: Int) : String
    fun getString(resourceId: Int, vararg  args: Any?): String
}