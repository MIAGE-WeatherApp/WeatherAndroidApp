package com.example.weather_project.api

import org.json.JSONObject
import java.net.URL

class WeatherAPI(
    private var apiKey: String
) {

    private lateinit var apiURL: String
    private lateinit var apiRead: String
    private lateinit var apiOnJson: JSONObject

    init {
        this.apiKey
    }

    fun getApiURL(): String {
        return this.apiKey
    }

    fun setApiURL(url: String){
        this.apiURL = url
    }

    fun callFirstAPI(lat: Double, long: Double): JSONObject {
        apiKey = this.getApiURL()
        apiURL =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=minutely,hourly&appid=$apiKey"
        apiRead = URL(apiURL).readText()
        apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    fun callSecondAPI(lat: Double, long: Double): JSONObject {
        apiKey = this.getApiURL()
        apiURL =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=minutely,hourly&appid=$apiKey"
        apiRead = URL(apiURL).readText()
        apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    fun getCurrentWeatherAPI(cityName: String): JSONObject {
        apiURL = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey"
        apiRead = URL(apiURL).readText()
        apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }
}