package com.insight.internaldiseasedetectionapp.data.retrofit

import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.DiagnosesResponse
import com.insight.internaldiseasedetectionapp.data.remote.user.UserLoginRequest
import com.insight.internaldiseasedetectionapp.data.remote.user.UserLoginResponse
import com.insight.internaldiseasedetectionapp.data.remote.user.UserRegisterRequest
import com.insight.internaldiseasedetectionapp.data.remote.user.UserRegisterResponse
import retrofit2.http.*

interface ApiService {
    @POST("auth/signup")
    suspend fun registerUser(
        @Body user: UserRegisterRequest
    ): UserRegisterResponse

    @POST("auth/login")
    suspend fun loginUser(
        @Body credentials: UserLoginRequest
    ): UserLoginResponse

    @GET("user/history")
    suspend fun getUserHistory(): DiagnosesResponse
}
