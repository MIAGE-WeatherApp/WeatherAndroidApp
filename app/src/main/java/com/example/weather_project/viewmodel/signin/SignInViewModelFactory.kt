package com.example.weather_project.viewmodel.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.usecase.UsersUseCase
import java.lang.IllegalArgumentException

class SignInViewModelFactory(
    private var usersUseCase: UsersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(usersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}