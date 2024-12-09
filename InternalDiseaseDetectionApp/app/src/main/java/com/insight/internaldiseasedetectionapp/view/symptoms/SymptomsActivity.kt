package com.insight.internaldiseasedetectionapp.view.symptoms

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.insight.internaldiseasedetectionapp.R
import com.insight.internaldiseasedetectionapp.data.remote.inference.InferenceRequest
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiConfig
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiService
import com.insight.internaldiseasedetectionapp.databinding.ActivitySymptomsBinding
import com.insight.internaldiseasedetectionapp.view.main.MainActivity
import kotlinx.coroutines.launch

class SymptomsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySymptomsBinding
    private lateinit var apiService: ApiService

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup name
        val sharedPreferences = this.getSharedPreferences("InSightPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("USER_NAME", null)
        if (!name.isNullOrEmpty()) {
            binding.symptomsTextView.text = "Hi, $name"
        }

        binding.symptomsPredictBtn.isEnabled = false

        // Setup Edit Text Validation & Predict Button
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.symptomsPredictBtn.isEnabled = s.isNotEmpty()
            }
        }

        binding.symptomsEditText.addTextChangedListener(textWatcher)

        apiService = ApiConfig.getApiService(this)
        setupAction(apiService)
    }

    @SuppressLint("InflateParams")
    private fun setupAction(apiService: ApiService) {
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.symptomsPredictBtn.setOnClickListener {
            binding.symptomsPredictBtn.isEnabled = false
            val userStory = binding.symptomsEditText.text.toString()

            lifecycleScope.launch {
                try {
                    val inferenceResponse = apiService.predictInternalDisease(InferenceRequest(userStory=userStory))
                    if (inferenceResponse.predictedDisease != null) {
                        val resultView = LayoutInflater.from(this@SymptomsActivity).inflate(R.layout.result_modal, null)

                        val resultTextView = resultView.findViewById<TextView>(R.id.resultDialogMessage)
                        resultTextView.text = inferenceResponse.predictedDisease

                        AlertDialog.Builder(this@SymptomsActivity).apply {
                            setTitle("Here is your result!")
                            setView(resultView)
                            setPositiveButton("Back to Home") { _, _ ->
                                finish()
                            }
                            setCancelable(false)
                            create()
                            show()
                            binding.symptomsPredictBtn.isEnabled = true
                        }
                    } else {
                        AlertDialog.Builder(this@SymptomsActivity).apply {
                            setTitle("Prediction Error")
                            setMessage("Try to predict again!")
                            setPositiveButton("Retry", null)
                            setCancelable(false)
                            create()
                            show()
                            binding.symptomsPredictBtn.isEnabled = true
                        }
                    }
                } catch (e: Exception) {
                    AlertDialog.Builder(this@SymptomsActivity).apply {
                        setTitle("Error")
                        setMessage("An error occurred: ${e.message}")
                        setPositiveButton("Retry", null)
                        setCancelable(false)
                        create()
                        show()
                        binding.symptomsPredictBtn.isEnabled = true
                    }
                }
            }
        }
    }
}