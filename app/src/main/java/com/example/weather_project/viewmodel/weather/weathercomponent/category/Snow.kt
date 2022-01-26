package com.example.weather_project.viewmodel.weather.weathercomponent.category

import androidx.annotation.IntRange
import androidx.annotation.NonNull

class Snow(
    @NonNull
    @IntRange(from = 0, to = 600)
    _snow_thickness: Int, // datatype

    @NonNull
    _snow_thickness_unit: SnowUnit
) : Category {

}