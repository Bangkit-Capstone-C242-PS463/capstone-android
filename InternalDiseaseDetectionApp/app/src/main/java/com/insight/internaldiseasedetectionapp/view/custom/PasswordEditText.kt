package com.insight.internaldiseasedetectionapp.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.insight.internaldiseasedetectionapp.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    var password: String? = null
    var isPasswordValid: Boolean = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                password = s.toString()
                isPasswordValid = password!!.length >= 8
                if (!isPasswordValid) {
                    setError(context.getString(R.string.password_warning), null)
                } else {
                    error = null
                }
            }
        })
    }
}