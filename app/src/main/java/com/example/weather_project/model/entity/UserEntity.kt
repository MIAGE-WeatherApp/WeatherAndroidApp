package com.example.weather_project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val user_id : Long = 0,

    @ColumnInfo(name = "user_name")
    val user_name : String,

    @ColumnInfo(name = "user_birthdate")
    val user_birthdate : String,

    @ColumnInfo(name = "user_city")
    val user_city : String,

    @ColumnInfo(name = "user_login")
    val user_login : String,

    @ColumnInfo(name = "user_password")
    val user_password : String
)

