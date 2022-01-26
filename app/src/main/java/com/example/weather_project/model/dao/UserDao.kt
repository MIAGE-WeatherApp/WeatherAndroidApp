package com.example.weather_project.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_project.model.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(user: List<UserEntity>): List<Long>

    @Update
    suspend fun setUser(user: UserEntity): Int

    @Delete
    suspend fun deleteUser(user: UserEntity): Int

    @Query(value = "SELECT * FROM User_table")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query(value = "SELECT * FROM User_table WHERE user_id LIKE :user_id")
    suspend fun getUserByUserId(user_id: Long): UserEntity? // Get user data details

    @Query(value = "SELECT * FROM User_table WHERE user_login LIKE :user_login AND user_password LIKE :user_password")
    fun getUserBySignInVerify(user_login: String, user_password: String): UserEntity? // Read Login data
}