package com.example.weather_project.viewmodel.weather

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.weather_project.api.WeatherAPI
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel() : ViewModel() {

    private var weatherAPI: WeatherAPI

    private val urlAPI = "d2c3d372129d6b440e76ab9b8fadc9f3"

    init {
        this.weatherAPI = WeatherAPI(this.urlAPI)
    }

    fun getAPI(): WeatherAPI {
        return this.weatherAPI
    }

    fun getCityName(appCompatActivity: AppCompatActivity, lat: Double, long: Double): String {
        var cityName: String = ""
        val geoCoder = Geocoder(appCompatActivity, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)

        cityName = address.get(0).locality

        return cityName
    }

    fun getCityTemp(cityName: String): Int {
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("main")
        val tempCity = (main.getInt("temp") - 273)
        return tempCity
    }

    fun getCityClimat(cityName: String): String {
        val weather =
            weatherAPI.getCurrentWeatherAPI(cityName).getJSONArray("weather").getJSONObject(0)
        val weatherState = weather.getString("description")
        return weatherState
    }

    fun getCityIconClimat(cityName: String): String {
        val weather =
            weatherAPI.getCurrentWeatherAPI(cityName).getJSONArray("weather").getJSONObject(0)
        val iconCity = weather.getString("icon")
        val urltoIcon = "https://openweathermap.org/img/w/$iconCity.png"
        return urltoIcon
    }

    fun getCityFeelsTemp(cityName: String): Double {
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("main")
        val feelslike = (main.getDouble("feels_like") - 273)
        return feelslike
    }

    fun getCityHumidity(cityName: String): Int {
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("main")
        val humiditycity = (main.getInt("humidity"))
        return humiditycity
    }

    fun getCityWind(cityName: String): Double {
        val wind = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("wind")
        val windSpeed = wind.getDouble("speed")
        return windSpeed
    }

    fun getAirQualityAPI(lat: Double, long: Double): JSONObject {
        val apiKey = this.weatherAPI.getApiURL()
        var apiURL = ""
        apiURL =
            "https://api.openweathermap.org/data/2.5/air_pollution?lat=$lat&lon=$long&appid=$apiKey"
        var apiRead = URL(apiURL).readText()
        val apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    fun getGeoTemp(lat: Double, long: Double): Int {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        val tempCurrent = (currentInfo.getInt("temp") - 273)
        return tempCurrent
    }

    fun getGeoClimatState(lat: Double, long: Double): String {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        val weather = currentInfo.getJSONArray("weather").getJSONObject(0)
        val weatherState = weather.getString("description")
        return weatherState
    }

    fun getGeoFeels(lat: Double, long: Double): String {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        var feelsCurrent = (currentInfo.getDouble("feels_like") - 273.75).toString()
        feelsCurrent = (BigDecimal(feelsCurrent).setScale(2, RoundingMode.HALF_EVEN)).toString()
        return feelsCurrent
    }

    fun getGeoIconURL(lat: Double, long: Double): String {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        val weather = currentInfo.getJSONArray("weather").getJSONObject(0)
        val iconString = weather.getString("icon")
        val iconURL = "https://openweathermap.org/img/w/$iconString.png"
        return iconURL
    }

    fun getGeoHumidity(lat: Double, long: Double): String {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        val humidity = currentInfo.getString("humidity")
        return humidity
    }

    fun getGeoWind(lat: Double, long: Double): Double {
        val currentInfo = this.getAPI().callSecondAPI(lat, long).getJSONObject("current")
        val windSpeed = currentInfo.getDouble("wind_speed")
        return windSpeed
    }

    fun getGeoAirQuality(lat: Double, long: Double): Int {
        val airInfo = getAirQualityAPI(lat, long)
        val list = airInfo.getJSONArray("list").getJSONObject(0).getJSONObject("main")
        var aqiGeo = list.getInt("aqi")
        return aqiGeo
    }

    fun getCityTempMin(cityName: String): Int {
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("main")
        val tempCityMin = (main.getInt("temp_min") - 273)
        return tempCityMin
    }


    fun getGeoTempMin(lat: Double, long: Double): Int {
        val currentInfo =
            this.getAPI().callFirstAPI(lat, long).getJSONArray("daily").getJSONObject(2)
                .getJSONObject("temp")
        val tempMinCurrent = (currentInfo.getInt("min") - 273)
        return tempMinCurrent
    }

    fun getGeoTempMax(lat: Double, long: Double): Int {
        val currentInfo =
            this.getAPI().callFirstAPI(lat, long).getJSONArray("daily").getJSONObject(2)
                .getJSONObject("temp")
        val tempMaxCurrent = (currentInfo.getInt("max") - 273)
        return tempMaxCurrent
    }

    fun getCityTempMax(cityName: String): Int {
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("main")
        val tempCityMax = (main.getInt("temp_max") - 273)
        return tempCityMax
    }

    fun getCitySunrise(cityName: String): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.FRANCE)
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("sys")
        val sunrise = sdf.format(main.getInt("sunrise") * 1000L)
        return sunrise
    }

    fun getCitySunset(cityName: String): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.FRANCE)
        val main = weatherAPI.getCurrentWeatherAPI(cityName).getJSONObject("sys")
        val sunset = sdf.format(main.getInt("sunset") * 1000L)
        return sunset
    }

    fun getGeoTempSunrise(lat: Double, long: Double): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.FRANCE)
        val currentInfo =
            this.getAPI().callFirstAPI(lat, long).getJSONArray("daily").getJSONObject(2)
        val geoSunrise = sdf.format(currentInfo.getInt("sunrise") * 1000L)
        return geoSunrise
    }

    fun getGeoTempSunset(lat: Double, long: Double): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.FRANCE)
        val currentInfo =
            this.getAPI().callFirstAPI(lat, long).getJSONArray("daily").getJSONObject(2)
        val geoSunset = sdf.format(currentInfo.getInt("sunset") * 1000L)
        return geoSunset
    }

}