package com.example.weather_project.viewmodel.weather.weathercomponent.category

import androidx.annotation.FloatRange
import androidx.annotation.NonNull

class Wind(
    @NonNull
    @FloatRange(from = 0.00)
    _speed: Float, // Datatype

    @NonNull
    _speed_unit: WindSpeedUnit,

    @FloatRange(from = 0.00)
    _wind_direction: Double? = null
) : Category {


}