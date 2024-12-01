package com.insight.internaldiseasedetectionapp.view.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.insight.internaldiseasedetectionapp.data.UserRepository
import com.insight.internaldiseasedetectionapp.data.retrofit.ApiService

class SymptomsViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var apiService: ApiService
}