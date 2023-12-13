package com.jnda.common.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.jnda.common.R
import com.jnda.common.getDefaultTypeFace

class CustomButton @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private enum class CustomButtonState(val value: Int) {
        DISABLED(0),
        ENABLED(1);

        companion object {
            fun getButtonState(value: Int) : CustomButtonState {
                return when (value) {
                    DISABLED.value -> DISABLED
                    ENABLED.value -> ENABLED
                    else -> throw IllegalStateException("Invalid button state")
                }
            }
        }
    }
    private var buttonState: CustomButtonState = CustomButtonState.DISABLED

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomButton, 0, 0
        ).apply {
            try {
                val fontWeight: Int = getInt(R.styleable.CustomButton_customFontWeight, 400) // def: regular
                this@CustomButton.typeface = getDefaultTypeFace(context, fontWeight)

                val textColor = getColor(R.styleable.CustomButton_customTextColor, ContextCompat.getColor(context, R.color.c_000000))
                this@CustomButton.setTextColor(textColor)

                buttonState = CustomButtonState.getButtonState(getInt(R.styleable.CustomButton_customButtonState, CustomButtonState.DISABLED.value))

                initView()
            } finally {
                recycle()
            }
        }
    }

    private fun initView() {
        when (buttonState) {
            CustomButtonState.ENABLED -> setActiveButton()
            CustomButtonState.DISABLED -> setInactiveButton()
        }
    }

    private fun setActiveButton() {
        this.background = ContextCompat.getDrawable(context, R.drawable.bg_button_active)
        this.setTextColor(ContextCompat.getColor(context, R.color.c_FFFFFF))
        this.isEnabled = true
    }

    private fun setInactiveButton() {
        this.background = ContextCompat.getDrawable(context, R.drawable.bg_button_inactive)
        this.setTextColor(ContextCompat.getColor(context, R.color.c_FFFFFF))
        this.isEnabled = false
    }

    /**
     * A method that determines if the button
     * should enabled and disabled
     */
    fun updateButtonState(isEnabled: Boolean = false) {
        if (isEnabled) {
            // enable button
            setActiveButton()
        } else {
            // disable button
            setInactiveButton()
        }
    }
}