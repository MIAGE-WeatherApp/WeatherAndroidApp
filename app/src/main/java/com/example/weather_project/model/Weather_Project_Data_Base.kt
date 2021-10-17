package com.example.weather_project.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_project.model.dao.User_Dao
import com.example.weather_project.model.entity.User_Entity
import java.security.AccessControlContext

@Database(entities = [User_Entity::class], version = 1, exportSchema = false)
abstract class Weather_Project_Data_Base : RoomDatabase() {

    abstract fun user_dao(): User_Dao

    companion object {
        @Volatile
        private var INSTANCE: Weather_Project_Data_Base? = null

        fun get_Data_Base(context: Context): Weather_Project_Data_Base {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Weather_Project_Data_Base::class.java,
                    "Weather_Project_Data_Base"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}