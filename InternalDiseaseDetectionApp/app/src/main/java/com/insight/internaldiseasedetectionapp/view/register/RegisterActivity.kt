package com.insight.internaldiseasedetectionapp.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.insight.internaldiseasedetectionapp.data.remote.user.UserRegisterRequest
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiConfig
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiService
import com.insight.internaldiseasedetectionapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiConfig.getApiService(this)

        setupView()
        setupAction(apiService)
        playAnimation()

        binding.signupButton.isEnabled = false

        // Setup Button
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.signupButton.isEnabled = binding.emailEditText.isEmailValid
                        && binding.passwordEditText.isPasswordValid
                        && (binding.emailEditText.email?.isNotEmpty() == true)
                        && (binding.passwordEditText.password?.isNotEmpty() == true)
            }
        }

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(apiService: ApiService) {
        binding.signupButton.setDebouncedOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                try {
                    val registerResponse = apiService.registerUser(UserRegisterRequest(name, email, password))
                    if (!registerResponse.error!!) {
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("Congratulations!")
                            setMessage("You successfully created an account!")
                            setPositiveButton("Continue") { _, _ ->
                                finish()
                            }
                            setCancelable(false)
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("Registration Failed")
                            setMessage(registerResponse.message)
                            setPositiveButton("Retry", null)
                            setCancelable(false)
                            create()
                            show()
                        }
                    }
                } catch (e: Exception) {
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Registration Error")
                        setMessage("An error occurred: ${e.message}")
                        setPositiveButton("Retry", null)
                        setCancelable(false)
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun View.setDebouncedOnClickListener(debounceTime: Long = 5000L, action: (View) -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime >= debounceTime) {
                    lastClickTime = currentTime
                    v.isEnabled = false
                    action(v)
                    v.postDelayed({ v.isEnabled = true }, debounceTime)
                }
            }
        })
    }

    private fun playAnimation() {
        val title =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(125)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(125)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(125)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(125)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(125)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(125)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(125)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(125)
        val signup =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(125)


        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 150
        }.start()
    }
}