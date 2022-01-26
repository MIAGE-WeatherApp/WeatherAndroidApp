package com.example.weather_project.viewmodel.signup

import android.util.Log
import androidx.lifecycle.*
import com.example.weather_project.model.entity.UserEntity
import com.example.weather_project.usecase.UsersUseCase
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.weather_project.resource.Resource

class SignUpViewModel(
    private var usersUseCase: UsersUseCase
) : ViewModel() {

    private val usersDataStatus = MutableLiveData<Resource<Long>>()
    val insertUserData: LiveData<Resource<Long>> = usersDataStatus

    init {
        this.usersUseCase
    }

    fun insertUserData(userEntity: UserEntity) {
        viewModelScope.launch {
            usersDataStatus.postValue(Resource.loading(null))
            try {
                val data = usersUseCase.addUser(userEntity)
                usersDataStatus.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                usersDataStatus.postValue(Resource.error(null, exception.message!!))
                Log.d("SIGNUP_ERROR", exception.message!!)
            }
        }
    }
}