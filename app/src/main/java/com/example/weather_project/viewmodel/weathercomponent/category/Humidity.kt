package com.example.weather_project.viewmodel.weathercomponent.category

import androidx.annotation.IntRange

class Humidity(

    @IntRange(from= 0, to =1)
    _humidity : Int? = null


) : Category {

}