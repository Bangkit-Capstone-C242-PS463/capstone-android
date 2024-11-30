package com.insight.internaldiseasedetectionapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insight.internaldiseasedetectionapp.R
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.ListDiagnosesItem

class DiagnosisAdapter(private val diagnoses: List<ListDiagnosesItem>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<DiagnosisViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosisViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diagnosis, parent, false)
        return DiagnosisViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosisViewHolder, position: Int) {
        val diagnosis = diagnoses[position]
        holder.bind(diagnosis)
    }

    override fun getItemCount(): Int {
        return diagnoses.size
    }
}