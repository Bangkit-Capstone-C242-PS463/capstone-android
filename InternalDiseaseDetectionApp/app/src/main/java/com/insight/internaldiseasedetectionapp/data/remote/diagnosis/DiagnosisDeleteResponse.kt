package com.insight.internaldiseasedetectionapp.data.remote.diagnosis

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DiagnosisDeleteResponse(
    val success: Boolean? = null,
) : Parcelable