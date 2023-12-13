package com.jnda.common.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.jnda.common.R
import com.jnda.common.getDefaultTypeFace

class CustomTextView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomTextView, 0, 0
        ).apply {
            try {
                val fontWeight: Int = getInt(R.styleable.CustomTextView_customFontWeight, 400) // def: regular
                this@CustomTextView.typeface = getDefaultTypeFace(context, fontWeight)

                val textColor = getColor(R.styleable.CustomTextView_customTextColor, ContextCompat.getColor(context, R.color.c_000000))
                this@CustomTextView.setTextColor(textColor)
            } finally {
                recycle()
            }
        }
    }
}