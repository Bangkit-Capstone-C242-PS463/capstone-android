package com.insight.internaldiseasedetectionapp.data.remote.user

data class UserRegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
