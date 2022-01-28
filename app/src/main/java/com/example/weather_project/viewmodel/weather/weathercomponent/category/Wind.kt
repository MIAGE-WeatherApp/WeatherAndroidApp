package com.example.weather_project.viewmodel.weather.weathercomponent.category

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import java.util.*

class Wind(
    @NonNull
    @FloatRange(from = 0.00)
    private var _speed: Double, // Datatype

    @FloatRange(from = 0.00)
    private var _wind_direction: Double? = null
) : Category {

    @NonNull
    private var _speed_unit: WindSpeedUnit

    init {
        this._speed
        this._speed_unit = WindSpeedUnit.KILOMETER_PER_HOUR
        this._wind_direction
    }

    fun setSpeedKMH(speed: Double) {
        this._speed = speed
    }

}