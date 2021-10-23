package com.example.weather_project.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_project.model.dao.UserDao
import com.example.weather_project.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class WeatherProjectDataBase : RoomDatabase() {

    abstract fun user_dao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherProjectDataBase? = null

        fun get_Data_Base(context: Context): WeatherProjectDataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherProjectDataBase::class.java,
                    "Weather_Project_Data_Base"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}