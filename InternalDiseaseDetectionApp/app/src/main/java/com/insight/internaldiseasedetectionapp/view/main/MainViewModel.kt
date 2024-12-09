package com.insight.internaldiseasedetectionapp.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.insight.internaldiseasedetectionapp.data.UserRepository
import com.insight.internaldiseasedetectionapp.data.pref.UserModel
import com.insight.internaldiseasedetectionapp.data.remote.diagnosis.ListDiagnosesItem
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiConfig
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _diagnoses = MutableLiveData<List<ListDiagnosesItem>>()
    val diagnoses: LiveData<List<ListDiagnosesItem>> = _diagnoses

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var apiService: ApiService

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun fetchUserHistory(context: Context) {
        apiService = ApiConfig.getApiService(context)
        _loading.value = true
        viewModelScope.launch {
            try {
                delay(1000L)
                val response = apiService.getUserHistory()
                if (response.history?.isNotEmpty() == true) {
                    val diagnoses = response.history.filterNotNull()
                    _diagnoses.value = diagnoses
                    sortDiagnosesByDate()
                } else {
                    _error.value = "No diagnoses history"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    private fun sortDiagnosesByDate() {
        _diagnoses.value?.let {
            _diagnoses.postValue(it.sortedByDescending {
                item -> ZonedDateTime.parse(item.createdAt, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            })
        }
    }

    fun deleteUserHistory(context: Context, historyId: String) {
        apiService = ApiConfig.getApiService(context)
        viewModelScope.launch {
            try {
                delay(1000L)
                val response = apiService.deleteUserHistory(historyId)
                if (response.success == true) {
                    // If delete succeed, fetch user history again
                    fetchUserHistory(context)
                } else {
                    _error.value = "Failed to delete user history"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}