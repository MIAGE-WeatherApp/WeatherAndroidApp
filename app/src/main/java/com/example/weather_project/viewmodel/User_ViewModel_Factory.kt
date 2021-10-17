package com.example.weather_project.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.model.repository.User_Repository
import java.lang.IllegalArgumentException

class User_ViewModel_Factory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(User_ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return User_ViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}