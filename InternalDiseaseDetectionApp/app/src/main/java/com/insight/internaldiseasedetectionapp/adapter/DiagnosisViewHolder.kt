package com.insight.internaldiseasedetectionapp.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.insight.internaldiseasedetectionapp.R
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.ListDiagnosesItem

class DiagnosisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val diseaseName: TextView = itemView.findViewById(R.id.disease_name)
    private val diagnosesDate: TextView = itemView.findViewById(R.id.diagnosis_date)

    fun bind(diagnosis: ListDiagnosesItem) {
        diseaseName.text = diagnosis.disease
        diagnosesDate.text = diagnosis.date
    }

}