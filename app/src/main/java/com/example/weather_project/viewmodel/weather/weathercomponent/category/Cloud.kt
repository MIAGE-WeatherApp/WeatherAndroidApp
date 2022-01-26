package com.example.weather_project.viewmodel.weather.weathercomponent.category

import androidx.annotation.FloatRange
import androidx.annotation.NonNull

class Cloud(
    @NonNull
    _type: CloudType,

    @FloatRange(from = 0.00, to = 750.00)
    _cloudiness : Double? = null
) : Category {


}