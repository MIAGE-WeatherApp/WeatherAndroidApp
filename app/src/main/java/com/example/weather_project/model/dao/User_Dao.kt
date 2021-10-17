package com.example.weather_project.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_project.model.entity.User_Entity

@Dao
interface User_Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add_User(User: User_Entity): Long

    @Update
    suspend fun set_User(User: User_Entity): Int

    @Delete
    suspend fun delete_User(User: User_Entity): Int

    @Query(value = "SELECT * FROM User_table")
    fun get_Users(): LiveData<List<User_Entity>>
}