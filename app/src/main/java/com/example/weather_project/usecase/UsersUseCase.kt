package com.example.weather_project.usecase

import com.example.weather_project.model.entity.UserEntity
import com.example.weather_project.model.repository.UserRepository

class UsersUseCase(
    private var userRepository: UserRepository
) {

    init {
        this.userRepository
    }

    suspend fun addUser(userEntity: UserEntity): Long {
        return userRepository.addUser(userEntity)
    }

    suspend fun addUserList(users: List<UserEntity>): List<Long> {
        return userRepository.addUserList(users)
    }

    fun getUserLoginVerify(user_login: String, user_password: String): UserEntity? {
        return userRepository.getUserBySignInVerify(user_login, user_password)
    }

    suspend fun getUserDataDetails(user_id: Long): UserEntity? {
        return userRepository.getUserByUserId(user_id)
    }

}