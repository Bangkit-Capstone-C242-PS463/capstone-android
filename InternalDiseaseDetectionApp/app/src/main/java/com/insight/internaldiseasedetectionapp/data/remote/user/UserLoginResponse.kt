package com.insight.internaldiseasedetectionapp.data.remote.user

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserLoginResponse(
	val loginResult: LoginResult? = null,
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class LoginResult(
	val name: String? = null,
	val userId: String? = null,
	val token: String? = null
) : Parcelable
