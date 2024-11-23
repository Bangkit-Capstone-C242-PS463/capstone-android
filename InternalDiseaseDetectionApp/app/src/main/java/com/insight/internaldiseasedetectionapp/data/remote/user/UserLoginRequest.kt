package com.insight.internaldiseasedetectionapp.data.remote.user

data class UserLoginRequest(
    val email: String,
    val password: String
)