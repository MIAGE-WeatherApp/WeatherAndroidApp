package com.example.weather_project.viewmodel.weathercomponent

import androidx.annotation.IntRange
import androidx.annotation.NonNull

class Air(
    @NonNull
    _name_Value: AirQualityNameValue,

    @IntRange(from = 0, to = 750)
    _limit: Int? = null
) {


}