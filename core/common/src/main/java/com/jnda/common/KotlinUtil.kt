package com.jnda.common

import java.util.Locale

object KotlinUtil {
    fun makeTitleCase(s: String?) : String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        return s.split(" ").joinToString(" ") { ss ->
            ss.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
        }
    }
}