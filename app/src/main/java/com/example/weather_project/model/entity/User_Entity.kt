package com.example.weather_project.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
data class User_Entity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val user_id : Long,

    @ColumnInfo(name = "user_name")
    val user_name : String,

    @ColumnInfo(name = "user_city")
    val user_city : String,

    @ColumnInfo(name = "user_login")
    val user_login : String,

    @ColumnInfo(name = "user_password")
    val user_password : String
)

