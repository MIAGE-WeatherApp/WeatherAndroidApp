package com.example.weather_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_project.model.WeatherProjectDataBase
import com.example.weather_project.model.entity.UserEntity
import com.example.weather_project.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val read_users: LiveData<List<UserEntity>>
    private val user_repository: UserRepository

    init {
        val user_dao = WeatherProjectDataBase.get_Data_Base(application).user_dao()
        user_repository = UserRepository(user_dao)
        read_users = user_repository.users
    }

    fun add_user(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.add_User(user)
        }
    }

    fun set_user(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.set_User(user)
        }
    }

    fun delete_user(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.delete_User(user)
        }
    }

    fun get_Users() {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.users
        }
    }


}