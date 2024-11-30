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
import com.insight.internaldiseasedetectionapp.adapter.DiagnosisAdapter
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.ListDiagnosesItem
import com.insight.internaldiseasedetectionapp.databinding.ActivityMainBinding
import com.insight.internaldiseasedetectionapp.view.ViewModelFactory
import com.insight.internaldiseasedetectionapp.view.symptoms.SymptomsActivity
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

//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            } else {
//                // To be implemented
//            }
//        }

        // Dummy Functions
        fun createDummyDiagnoses(): List<ListDiagnosesItem> {
            return listOf(
                ListDiagnosesItem("Common Cold", "Thursday, 28 November 2024"),
                ListDiagnosesItem("Flu", "Monday, 25 November 2024"),
                ListDiagnosesItem("Chickenpox", "Friday, 15 November 2024"),
                ListDiagnosesItem("Measles", "Sunday, 10 November 2024")
            )
        }
        fun createEmptyDiagnoses(): List<ListDiagnosesItem> {
            return listOf()
        }

        // TODO: Fetch from API
        val diagnoses: List<ListDiagnosesItem> = createDummyDiagnoses()

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

        // Diagnoses History Recycler View
        val diagnosesAdapter = DiagnosisAdapter(diagnoses) {}
        recyclerView.adapter = diagnosesAdapter

        setupAction()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, SymptomsActivity::class.java)
            startActivity(intent)

        }
    }
}