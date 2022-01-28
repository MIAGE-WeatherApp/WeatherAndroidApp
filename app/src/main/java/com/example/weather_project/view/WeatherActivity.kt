package com.example.weather_project.view

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.weather_project.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_weather_display.*
import org.json.JSONObject
import java.net.URL
import java.util.*
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.SearchView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


class WeatherActivity : AppCompatActivity() {

    //Initializing variables related to Locations
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_display)

        //Permet de réaliser des requêtes http en parallèle
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        weatherSearchId.queryHint = "Search for a city :)"
    }


    @SuppressLint("MissingPermission") //Change this srh
    fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            var location: Location? = task.result
            if (location == null) {
                NewLocationData()
            } else {

                weatherTitleId.text = getCityName(location.latitude, location.longitude)
                getCurrentWeatherbyLocation(location.latitude, location.longitude)
                getOneCallAPI(location.latitude, location.longitude)
                getCurrentWeatherAPI(getCityName(location.latitude, location.longitude))
                searchByCity()
            }
        }
    }


    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 3)

        cityName = address.get(0).locality
        countryName = address.get(0).countryName
        Log.d("SARAH :", "CHUI LA2.0")
        Log.d(
            "Debug:",
            "J'habiiiite aaaaa: $cityName ; Dans les pays des rêves :$countryName"
        )
        return cityName
    }

    @SuppressLint("MissingPermission")
    fun NewLocationData() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest, locationCallback, it
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            weatherTitleId.text =
                getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
        }
    }

    fun getOneCallAPI(lat: Double, long: Double): JSONObject {
        var apiKey = "d2c3d372129d6b440e76ab9b8fadc9f3"
        var apiURL = ""
        apiURL =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=minutely,hourly&appid=$apiKey"
        var apiRead = URL(apiURL).readText()
        val apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    fun getCurrentWeatherAPI(cityName: String): JSONObject {
        var apiKey = "d2c3d372129d6b440e76ab9b8fadc9f3"
        var apiURL = ""
        apiURL = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey"
        var apiRead = URL(apiURL).readText()
        val apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    fun getCityTemp(cityName: String): Int {
        val main = getCurrentWeatherAPI(cityName).getJSONObject("main")
        val tempCity = (main.getInt("temp") - 273)
        return tempCity
    }

    fun getCityClimat(cityName: String): String {
        val weather = getCurrentWeatherAPI(cityName).getJSONArray("weather").getJSONObject(0)
        val weatherState = weather.getString("description")
        return weatherState
    }

    fun getCityIconClimat(cityName: String): String {
        val weather = getCurrentWeatherAPI(cityName).getJSONArray("weather").getJSONObject(0)
        val iconCity = weather.getString("icon")
        val urltoIcon = "https://openweathermap.org/img/w/$iconCity.png"
        return urltoIcon
    }

    fun getCityFeelsTemp(cityName: String): Double {
        val main = getCurrentWeatherAPI(cityName).getJSONObject("main")
        val feelslike = (main.getDouble("feels_like") - 273)
        return feelslike
    }

    fun getCityHumidity(cityName: String): Int {
        val main = getCurrentWeatherAPI(cityName).getJSONObject("main")
        val humiditycity = (main.getInt("humidity"))
        return humiditycity
    }

    fun getCityWind(cityName: String): Double {
        val wind = getCurrentWeatherAPI(cityName).getJSONObject("wind")
        val windSpeed = wind.getDouble("speed")
        return windSpeed
    }

    fun getAirQualityAPI(lat: Double, long: Double): JSONObject {
        val apiKey = "d2c3d372129d6b440e76ab9b8fadc9f3"
        var apiURL = ""
        apiURL =
            "https://api.openweathermap.org/data/2.5/air_pollution?lat=$lat&lon=$long&appid=$apiKey"
        var apiRead = URL(apiURL).readText()
        val apiOnJson = JSONObject(apiRead)
        return apiOnJson
    }

    //EN KELVIN DONC VOIR COMMENT ON UTILISE LES CELSIUS OU KELVIN OU FAHRENHEIT
    fun getGeoTemp(lat: Double, long: Double): Int {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        val tempCurrent = (currentInfo.getInt("temp") - 273)
        return tempCurrent
    }

    fun getGeoClimatState(lat: Double, long: Double): String {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        val weather = currentInfo.getJSONArray("weather").getJSONObject(0)
        val weatherState = weather.getString("description")
        return weatherState
    }

    fun getGeoFeels(lat: Double, long: Double): String {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        var feelsCurrent = (currentInfo.getDouble("feels_like") - 273.75).toString()
        feelsCurrent = (BigDecimal(feelsCurrent).setScale(2, RoundingMode.HALF_EVEN)).toString()
        return feelsCurrent
    }

    fun getGeoIconURL(lat: Double, long: Double): String {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        val weather = currentInfo.getJSONArray("weather").getJSONObject(0)
        val iconString = weather.getString("icon")
        val iconURL = "https://openweathermap.org/img/w/$iconString.png"
        return iconURL
    }

    fun getGeoHumidity(lat: Double, long: Double): String {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        val humidity = currentInfo.getString("humidity")
        return humidity
    }

    fun getGeoWind(lat: Double, long: Double): Double {
        val currentInfo = getOneCallAPI(lat, long).getJSONObject("current")
        val windSpeed = currentInfo.getDouble("wind_speed")
        return windSpeed
    }

    fun getGeoAirQuality(lat: Double, long: Double): Int {
        val airInfo = getAirQualityAPI(lat, long)
        val list = airInfo.getJSONArray("list").getJSONObject(0).getJSONObject("main")
        var aqiGeo = list.getInt("aqi")
        return aqiGeo

    }

    //ICI UNIQUEMENT LES RECUPERATIONS PAS DE PRISE DE VALEUR DES MODELVIEW DONC LA PROCHAINE FOIS PRENDRE CONNAISSANCE CA COMME LES UNITES
    @SuppressLint("ResourceType")
    fun getCurrentWeatherbyLocation(lat: Double?, long: Double?) {
        //Pour la température en celsius

        val tempGeo = lat?.let { long?.let { it1 -> getGeoTemp(it, it1) } }
        Log.d("TEMPERATURE DE CHEZ MOI", "$tempGeo°C")
        weatherTempId.text = tempGeo.toString() + "°C"

        //Pour l'état du climat
        val ClimatGeo = lat?.let { long?.let { it1 -> getGeoClimatState(it, it1) } }
        ClimatGeo?.let { Log.d("STATEWEATHER", it) }

        if (ClimatGeo != null) {
            weatherDescriptionId.text = ClimatGeo.uppercase()
        }

        //Pour l'icône du climat
        val iconGeo = lat?.let { long?.let { it1 -> getGeoIconURL(it, it1) } }
        iconGeo?.let { Log.d("STATECLIMAT", it) }
        Glide.with(this).load(iconGeo).into(weatherStateId)

        //Pour l'humidité
        val humidityGeo = lat?.let { long?.let { it1 -> getGeoHumidity(it, it1) } }
        humidityGeo?.let { Log.d("HUMIDITY", it) }
        weatherHumidityId.text = humidityGeo.toString() + "%"

        //Pour la température ressentie (Penser à faire lier avec les unités)
        var feelslike = lat?.let { long?.let { it1 -> getGeoFeels(it, it1) } }
        feelslike?.let { Log.d("RESSENTIE ", it) }
        weatherFeltId.text = feelslike.toString() + " °C"

        //Pour la vitesse du vent
        val windSpeed = lat?.let { long?.let { it1 -> getGeoWind(it, it1).toString() } }
        windSpeed?.let { Log.d("WIIIIND", it) }
        weatherWindId.text = windSpeed.toString() + " km/h"

        //Pour la qualité de l'air
        weatherQualityId.text =
            lat?.let { long?.let { it1 -> getGeoAirQuality(it, it1).toString() } }

    }

    private fun searchByCity() {

        weatherSearchId.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                try {
                    //GET TEMPERATURE BY CITY
                    val tempCity = query?.let { getCityTemp(it) }
                    weatherTempId.text = "$tempCity°C"

                    //GET CITYNAME
                    val cityName = query
                    if (cityName != null) {
                        weatherTitleId.text =
                            cityName.replaceFirstChar { cityName.substring(0, 1).uppercase() }
                    }
                    //GET ICONCLIMAT
                    Glide.with(this@WeatherActivity).load(query?.let { getCityIconClimat(it) })
                        .into(weatherStateId)

                    //GET CLIMAT BY CITY
                    val weatherState = query?.let { getCityClimat(it) }
                    if (weatherState != null) {
                        weatherDescriptionId.text = weatherState.uppercase()
                    }
                    //GET FEELSTEMP BY CITY
                    val feelslike = query?.let { getCityFeelsTemp(it) }
                    weatherFeltId.text = ((feelslike?.let {
                        BigDecimal(it).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        )
                    }).toString() + "°C")

                    //GET FEELSTEMP BY CITY
                    val humidityCity = query?.let { getCityHumidity(it) }
                    if (humidityCity != null) {
                        weatherHumidityId.text = "$humidityCity %"
                    }

                    val windspeedCity = query?.let { getCityWind(it) }
                    if (windspeedCity != null) {
                        weatherWindId.text = "$windspeedCity km/h"
                    }

                } catch (MalformedURLException: IOException) {
                    Toast.makeText(
                        this@WeatherActivity,
                        "Votre ville n'existe pas !",
                        Toast.LENGTH_LONG
                    ).show()


                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                //NOT YET IMPLEMENTED
                return false
            }

        })

    }


}

