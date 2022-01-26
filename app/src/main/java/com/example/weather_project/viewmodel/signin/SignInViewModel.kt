package com.example.weather_project.viewmodel.signin

import android.util.Log
import androidx.lifecycle.*
import com.example.weather_project.model.entity.UserEntity
import com.example.weather_project.resource.Resource
import com.example.weather_project.usecase.UsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel
constructor(
    private var usersUseCase: UsersUseCase
) : ViewModel() {

    private val userLoginDataStatus = MutableLiveData<Resource<UserEntity>>()
    val getUserLoginDataStatus: MutableLiveData<Resource<UserEntity>> = userLoginDataStatus

    init {
        this.usersUseCase
    }

    fun getUserLoginDataStatus(user_login: String, user_password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userLoginDataStatus.postValue(Resource.loading(null))
            try {
                val data = usersUseCase.getUserLoginVerify(user_login, user_password)
                userLoginDataStatus.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                userLoginDataStatus.postValue(Resource.error(null, exception.message!!))
                Log.d("SIGNING_ERROR", exception.message!!)
            }
        }
    }
}