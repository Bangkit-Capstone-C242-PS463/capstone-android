package com.insight.internaldiseasedetectionapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.insight.internaldiseasedetectionapp.data.pref.UserModel
import com.insight.internaldiseasedetectionapp.data.remote.user.UserLoginRequest
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiConfig
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiService
import com.insight.internaldiseasedetectionapp.databinding.ActivityLoginBinding
import com.insight.internaldiseasedetectionapp.view.ViewModelFactory
import com.insight.internaldiseasedetectionapp.view.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiConfig.getApiService(this)

        setupView()
        setupAction(apiService)
        playAnimation()

        binding.loginButton.isEnabled = false

        // Setup Button
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.loginButton.isEnabled = binding.emailEditText.isEmailValid
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
        binding.loginButton.setDebouncedOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                try {
                    val loginResponse = apiService.loginUser(UserLoginRequest(email, password))

                    if (!loginResponse.error!!) {
                        viewModel.saveSession(UserModel(email, loginResponse.loginResult?.token.toString()))

                        val sharedPreferences = getSharedPreferences("InSightPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("USER_TOKEN", loginResponse.loginResult?.token.toString())
                        editor.apply()

                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Login Success!")
                            setMessage("Welcome back, ${loginResponse.loginResult?.name}!")
                            setPositiveButton("Continue") { _, _ ->
                                ViewModelFactory.clearInstance()
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            setCancelable(false)
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Login Error")
                            setMessage(loginResponse.message)
                            setPositiveButton("Retry", null)
                            setCancelable(false)
                            create()
                            show()
                        }
                    }
                } catch (e: Exception) {
                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Error")
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
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -45f, 45f).apply {
            duration = 4500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(125)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(125)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(125)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(125)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(125)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(125)
        val login =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(125)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 150
        }.start()
    }
}
