package com.example.weather_project.model.repository

import androidx.lifecycle.LiveData
import com.example.weather_project.model.dao.UserDao
import com.example.weather_project.model.entity.UserEntity

class UserRepository(private val user_dao: UserDao) {

    suspend fun addUser(user_entity: UserEntity): Long = user_dao.addUser(user_entity)

    suspend fun addUserList(users: List<UserEntity>): List<Long> = user_dao.insertAllUser(users)

    suspend fun setUser(user_entity: UserEntity): Int = user_dao.setUser(user_entity)

    suspend fun deleteUser(user_entity: UserEntity): Int = user_dao.deleteUser(user_entity)

    val users: LiveData<List<UserEntity>> = user_dao.getUsers()

    suspend fun getUserByUserId(user_id: Long): UserEntity? =
        user_dao.getUserByUserId(user_id)

    fun getUserBySignInVerify(user_login: String, user_password: String): UserEntity? =
        user_dao.getUserBySignInVerify(user_login, user_password)

}