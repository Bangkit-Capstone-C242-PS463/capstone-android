package com.insight.internaldiseasedetectionapp.data.remote.diagnosis

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DiagnosesResponse(
	@SerializedName("history")
	val history: List<ListDiagnosesItem?>? = null
) : Parcelable

@Parcelize
data class ListDiagnosesItem(
	@SerializedName("id")
	val id: String? = null,

	@SerializedName("user_id")
	val userId: String? = null,

	@SerializedName("result")
	val result: String? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("updated_at")
	val updatedAt: String? = null
) : Parcelable
