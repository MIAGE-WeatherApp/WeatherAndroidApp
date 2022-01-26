package com.example.weather_project.viewmodel.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.usecase.UsersUseCase
import java.lang.IllegalArgumentException

class SignUpViewModelFactory(
    private var usersUseCase: UsersUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(usersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}