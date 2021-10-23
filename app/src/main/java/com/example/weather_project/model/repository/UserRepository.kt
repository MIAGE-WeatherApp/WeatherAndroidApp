package com.example.weather_project.model.repository

import androidx.lifecycle.LiveData
import com.example.weather_project.model.dao.UserDao
import com.example.weather_project.model.entity.UserEntity

class UserRepository(private val user_dao: UserDao) {

    suspend fun add_User(user_entity: UserEntity): Long = user_dao.add_User(user_entity)

    suspend fun set_User(user_entity: UserEntity): Int = user_dao.set_User(user_entity)

    suspend fun delete_User(user_entity: UserEntity): Int = user_dao.delete_User(user_entity)

    val users: LiveData<List<UserEntity>> = user_dao.get_Users()

    suspend fun get_User_By_User_id(user_id: Long): UserEntity? = user_dao.get_User_By_User_Id(user_id)

}