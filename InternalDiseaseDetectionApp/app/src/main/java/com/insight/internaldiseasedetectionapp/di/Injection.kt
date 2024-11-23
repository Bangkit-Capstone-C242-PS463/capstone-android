package com.insight.internaldiseasedetectionapp.di

import android.content.Context
import com.insight.internaldiseasedetectionapp.data.UserRepository
import com.insight.internaldiseasedetectionapp.data.pref.UserPreference
import com.insight.internaldiseasedetectionapp.data.pref.dataStore
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        ApiConfig.createService(context)
        return UserRepository.getInstance(pref)
    }
}