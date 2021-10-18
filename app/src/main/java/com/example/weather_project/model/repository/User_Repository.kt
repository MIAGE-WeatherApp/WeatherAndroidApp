package com.example.weather_project.model.repository

import androidx.lifecycle.LiveData
import com.example.weather_project.model.dao.User_Dao
import com.example.weather_project.model.entity.User_Entity

class User_Repository(private val user_dao: User_Dao) {

    suspend fun add_User(user_entity: User_Entity): Long = user_dao.add_User(user_entity)

    suspend fun set_User(user_entity: User_Entity): Int = user_dao.set_User(user_entity)

    suspend fun delete_User(user_entity: User_Entity): Int = user_dao.delete_User(user_entity)

    val users: LiveData<List<User_Entity>> = user_dao.get_Users()
}