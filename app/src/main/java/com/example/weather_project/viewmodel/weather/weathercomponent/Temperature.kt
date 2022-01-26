package com.example.weather_project.viewmodel.weather.weathercomponent

import androidx.annotation.IntRange
import androidx.annotation.NonNull

class Temperature(
    @NonNull
    @IntRange(from = -100, to = 100)
    val _exact_value: Int, // Datatype

    val _temperature_unit: TemperatureUnit,

    @NonNull
    @IntRange(from = -100, to = 100)
    val _min_value: Int, // Datatype

    @NonNull
    @IntRange(from = -100, to = 100)
    val _max_value: Int, // Datatype

    @NonNull
    @IntRange(from = -100, to = 100)
    val _felt_value: Int, // Datatype
) {


}
