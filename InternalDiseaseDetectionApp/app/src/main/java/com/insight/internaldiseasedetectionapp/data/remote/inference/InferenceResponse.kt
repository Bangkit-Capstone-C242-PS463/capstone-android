package com.insight.internaldiseasedetectionapp.data.remote.inference

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class InferenceResponse(
    @SerializedName("predicted_disease")
    val predictedDisease: String? = null,

    @SerializedName("identified_symptoms")
    val identifiedSymptoms: List<String?>? = null,
) : Parcelable