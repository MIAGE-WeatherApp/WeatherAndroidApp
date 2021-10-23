package com.example.weather_project.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_project.model.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add_User(user: UserEntity): Long

    @Update
    suspend fun set_User(user: UserEntity): Int

    @Delete
    suspend fun delete_User(user: UserEntity): Int

    @Query(value = "SELECT * FROM User_table")
    fun get_Users(): LiveData<List<UserEntity>>

    @Query(value = "SELECT * FROM User_table WHERE user_id LIKE :user_id")
    suspend fun get_User_By_User_Id(user_id: Long): UserEntity?

}