package com.insight.internaldiseasedetectionapp.data.remote.user

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserRegisterResponse(
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable
