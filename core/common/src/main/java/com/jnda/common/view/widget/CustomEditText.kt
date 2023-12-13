package com.jnda.common.view.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.jnda.common.R
import com.jnda.common.databinding.CustomEditTextBinding

class CustomEditText @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val editTextLayout: CustomEditTextBinding =
        CustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var inputType: Int = 1

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            0, 0
        ).apply {
            try {
                val headerText: String? = getString(R.styleable.CustomEditText_customHeaderText)
                val placeHolder: String? = getString(R.styleable.CustomEditText_customPlaceholder)
                inputType = getInt(R.styleable.CustomEditText_customInputType, 1) // text

                editTextLayout.tvHeader.text = headerText
                editTextLayout.etInput.hint = placeHolder

                initView()
            } finally {
                recycle()
            }
        }
    }

    private fun initView() {
        editTextLayout.etInput.maxLines = 1
        if (inputType == 1) {
            // text
            editTextLayout.etInput.inputType = InputType.TYPE_CLASS_TEXT
        } else if (inputType == 2) {
            // password
            editTextLayout.etInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    fun getText() : String {
        return editTextLayout.etInput.text.toString()
    }

    fun clearText() {
        editTextLayout.etInput.setText("")
    }

    fun onAfterTextChanged(onAfterTextChanged: () -> Unit) {
        editTextLayout.etInput.doAfterTextChanged { onAfterTextChanged.invoke() }
    }
}