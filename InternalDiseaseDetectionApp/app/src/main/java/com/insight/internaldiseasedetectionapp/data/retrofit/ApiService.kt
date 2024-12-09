package com.insight.internaldiseasedetectionapp.data.retrofit

import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.DiagnosesResponse
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.DiagnosisDeleteResponse
import com.insight.internaldiseasedetectionapp.data.remote.inference.InferenceRequest
import com.insight.internaldiseasedetectionapp.data.remote.inference.InferenceResponse
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

    @DELETE("user/history")
    suspend fun deleteUserHistory(
        @Query("history_id") historyId: String
    ): DiagnosisDeleteResponse

    @GET("user/history")
    suspend fun getUserHistory(): DiagnosesResponse

    @POST("predict")
    suspend fun predictInternalDisease(
        @Body userText: InferenceRequest
    ): InferenceResponse
}
