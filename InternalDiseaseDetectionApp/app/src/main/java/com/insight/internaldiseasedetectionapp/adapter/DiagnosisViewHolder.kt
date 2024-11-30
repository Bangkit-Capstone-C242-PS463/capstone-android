package com.insight.internaldiseasedetectionapp.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.insight.internaldiseasedetectionapp.R
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.ListDiagnosesItem

class DiagnosisViewHolder(itemView: View, private val onDeleteClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val diseaseName: TextView = itemView.findViewById(R.id.disease_name)
    private val diagnosesDate: TextView = itemView.findViewById(R.id.diagnosis_date)
    private val trashButton: ImageButton = itemView.findViewById(R.id.trashButton)

    fun bind(diagnosis: ListDiagnosesItem) {
        diseaseName.text = diagnosis.result
        diagnosesDate.text = diagnosis.createdAt
        trashButton.setOnClickListener { onDeleteClick(diagnosis.id ?: "") }
    }
}