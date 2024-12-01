package com.insight.internaldiseasedetectionapp.view.symptoms

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.insight.internaldiseasedetectionapp.R
import com.insight.internaldiseasedetectionapp.databinding.ActivitySymptomsBinding
import com.insight.internaldiseasedetectionapp.view.ViewModelFactory
import com.insight.internaldiseasedetectionapp.view.main.MainActivity
import com.insight.internaldiseasedetectionapp.view.main.MainViewModel

class SymptomsActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySymptomsBinding

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

        setupAction()
    }

    @SuppressLint("InflateParams")
    private fun setupAction() {
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.symptomsPredictBtn.setOnClickListener {
            val resultView = LayoutInflater.from(this).inflate(R.layout.result_modal, null)
            AlertDialog.Builder(this).apply {
                setTitle("Here is your result!")
                setView(resultView)
                setPositiveButton("Back to Home") { _, _ ->
                    finish()
                }
                setCancelable(false)
                create()
                show()
            }
        }
    }
}