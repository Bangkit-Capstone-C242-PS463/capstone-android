package com.insight.internaldiseasedetectionapp.data.retrofit

import android.content.Context
import com.insight.internaldiseasedetectionapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        var apiService: ApiService? = null

        fun getApiService(context: Context): ApiService {
            val loggingInterceptor =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }

            val sharedPreferences = context.getSharedPreferences("InSightPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("USER_TOKEN", null)

            val client = if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            apiService = retrofit.create(ApiService::class.java)
            return apiService as ApiService
        }

        fun createService(context: Context) {
            apiService = null
            getApiService(context)
        }
    }
}