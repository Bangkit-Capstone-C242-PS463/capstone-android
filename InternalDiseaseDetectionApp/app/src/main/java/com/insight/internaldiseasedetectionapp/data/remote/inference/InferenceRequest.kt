package com.insight.internaldiseasedetectionapp.data.remote.inference

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InferenceRequest(
    val userStory: String? = null,
) : Parcelable