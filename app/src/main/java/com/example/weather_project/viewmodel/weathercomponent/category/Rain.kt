package com.example.weather_project.viewmodel.weathercomponent.category

import androidx.annotation.FloatRange
import androidx.annotation.NonNull

class Rain(
    @NonNull
    @FloatRange(from = 0.00, to = 1.00)
    _rate_precipitation: Float // Datatype
) : Category {

}

