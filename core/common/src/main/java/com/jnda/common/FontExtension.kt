package com.jnda.common

import android.content.Context
import android.graphics.Typeface

fun getDefaultTypeFace(context: Context, fontWeight: Int) : Typeface? {
    val font = when (fontWeight) {
        400 -> "Inter-Regular.ttf"
        500 -> "Inter-Medium.ttf"
        700 -> "Inter-Bold.ttf"
        else -> "Inter-Regular.ttf"
    }
    val builder = Typeface.Builder(context.assets,"fonts/$font")
    return builder.build()
}