package com.example.weather_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_project.model.Weather_Project_Data_Base
import com.example.weather_project.model.entity.User_Entity
import com.example.weather_project.model.repository.User_Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class User_ViewModel(application: Application) : AndroidViewModel(application) {

    private val read_users: LiveData<List<User_Entity>>
    private val user_repository: User_Repository

    init {
        val user_dao = Weather_Project_Data_Base.get_Data_Base(application).user_dao()
        user_repository = User_Repository(user_dao)
        read_users = user_repository.users
    }

    fun add_user(user: User_Entity) {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.add_User(user)
        }
    }

    fun set_user(user: User_Entity) {
        viewModelScope.launch(Dispatchers.IO) {
            user_repository.set_User(user)
        }
    }

    fun delete_user(user: User_Entity) {
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