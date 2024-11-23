package com.insight.internaldiseasedetectionapp.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.insight.internaldiseasedetectionapp.R

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    var email: String? = null
    var isEmailValid: Boolean = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                email = s.toString()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                isEmailValid = s.matches(emailPattern.toRegex())
                if (!isEmailValid) {
                    setError(context.getString(R.string.email_warning), null)
                } else {
                    error = null
                }
            }
        })
    }
}