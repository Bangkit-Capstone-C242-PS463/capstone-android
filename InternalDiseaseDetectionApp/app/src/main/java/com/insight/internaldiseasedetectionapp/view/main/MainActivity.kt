package com.insight.internaldiseasedetectionapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.Diagnosis
import com.insight.internaldiseasedetectionapp.databinding.ActivityMainBinding
import com.insight.internaldiseasedetectionapp.view.ViewModelFactory
import com.insight.internaldiseasedetectionapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageHome: ImageView
    private lateinit var noDiagnosesTitle: TextView
    private lateinit var noDiagnosesDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Recycler View for Disease History
        recyclerView = binding.recyclerViewMain
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup No Diagnoses Components
        imageHome = binding.imageHome
        noDiagnosesTitle = binding.noDiagnosesTitle
        noDiagnosesDesc = binding.noDiagnosesDesc

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                // To be implemented
            }
        }

        // Dummy Functions
        fun createDummyDiagnoses(): List<Diagnosis> {
            return listOf(
                Diagnosis("Common Cold", "2024-11-28"),
                Diagnosis("Flu", "2024-11-25"),
                Diagnosis("Chickenpox", "2024-11-15"),
                Diagnosis("Measles", "2024-11-10")
            )
        }
        fun createEmptyDiagnoses(): List<Diagnosis> {
            return listOf()
        }

        // TODO: Fetch from API
        val diagnoses: List<Diagnosis> = createEmptyDiagnoses()

        // Handle Home Display Condition (Diagnoses / No Diagnoses Available)
        if (diagnoses.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            imageHome.visibility = View.GONE
            noDiagnosesTitle.visibility = View.GONE
            noDiagnosesDesc.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            imageHome.visibility = View.VISIBLE
            noDiagnosesTitle.visibility = View.VISIBLE
            noDiagnosesDesc.visibility = View.VISIBLE
        }

        setupAction()
    }

    // TODO: Setup FAB button
    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}