package com.insight.internaldiseasedetectionapp.view.symptoms

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.insight.internaldiseasedetectionapp.databinding.ActivitySymptomsBinding
import com.insight.internaldiseasedetectionapp.view.main.MainActivity

class SymptomsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySymptomsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}