package com.insight.internaldiseasedetectionapp.data.remote.user

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserLoginResponse(
	val id: String? = null,
	val email: String? = null,
	val name: String? = null,
	val access_token: String? = null
) : Parcelable