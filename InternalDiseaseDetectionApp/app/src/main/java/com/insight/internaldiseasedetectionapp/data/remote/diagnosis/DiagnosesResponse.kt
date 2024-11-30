package com.insight.internaldiseasedetectionapp.data.remote.diagnosis

import com.google.gson.annotations.SerializedName

data class DiagnosesResponse(

	@field:SerializedName("listEvents")
	val listDiagnoses: List<ListDiagnosesItem?>? = null,
)

data class ListDiagnosesItem(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("date")
	val date: String? = null,
)

