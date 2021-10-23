package com.example.weather_project.viewmodel.weathercomponent.category

import androidx.annotation.IntRange
import androidx.annotation.NonNull

class Sun(
    @NonNull
    @IntRange(from = 1, to = 11)
    _index_UV: Int, // Datatype

    _sunrise : String? = null,
    _sunset : String? = null

) : Category {


}