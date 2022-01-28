package com.example.weather_project.view

import android.annotation.SuppressLint
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
import java.util.*
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weather_project.viewmodel.weather.WeatherViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


class WeatherActivity : AppCompatActivity() {

    // Initializing variables related to Locations
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // Initializing variables related to ViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_display)

        //Permet de réaliser des requêtes http en parallèle
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        weatherSearchId.queryHint = "Search for a weather country"

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    /**
     * Location features
     */
    @SuppressLint("MissingPermission") //Change this srh
    fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            var location: Location? = task.result
            if (location == null) {
                newLocationData()
            } else {
                weatherTitleId.text =
                    weatherViewModel.getCityName(this, location.latitude, location.longitude)
                getCurrentWeatherbyLocation(location.latitude, location.longitude)
                weatherViewModel.getAPI().callSecondAPI(location.latitude, location.longitude)
                weatherViewModel.getAPI().getCurrentWeatherAPI(
                    weatherViewModel.getCityName(
                        this,
                        location.latitude,
                        location.longitude
                    )
                )
                searchByCity()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun newLocationData() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Pas dans le ViewModel
        Looper.myLooper()?.let {
            fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest, locationCallback, it
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            weatherTitleId.text =
                weatherViewModel.getCityName(
                    this@WeatherActivity,
                    lastLocation.latitude,
                    lastLocation.longitude
                )
        }
    }

    //ICI UNIQUEMENT LES RECUPERATIONS PAS DE PRISE DE VALEUR DES MODELVIEW DONC LA PROCHAINE FOIS PRENDRE CONNAISSANCE CA COMME LES UNITES
    @SuppressLint("ResourceType")
    fun getCurrentWeatherbyLocation(lat: Double?, long: Double?) {
        //Pour la température en celsius

        val tempGeo = lat?.let { long?.let { it1 -> weatherViewModel.getGeoTemp(it, it1) } }
        Log.d("TEMPERATURE DE CHEZ MOI", "$tempGeo°C")
        weatherTempId.text = tempGeo.toString() + "°C"

        // Changer le .text
        val tempMinGeo = lat?.let { long?.let { it1 -> weatherViewModel.getGeoTempMin(it, it1) } }
        Log.d("TEMPERATURE MIN DE CHEZ MOI", "$tempMinGeo°C")
        //weatherTempId.text = tempGeo.toString() + "°C"

        // Changer le .text
        val tempMaxGeo = lat?.let { long?.let { it1 -> weatherViewModel.getGeoTempMax(it, it1) } }
        Log.d("TEMPERATURE MAX DE CHEZ MOI", "$tempMaxGeo°C")
        //weatherTempId.text = tempGeo.toString() + "°C"

        // Changer le .text
        val geoSunrise =
            lat?.let { long?.let { it1 -> weatherViewModel.getGeoTempSunrise(it, it1) } }
        Log.d("Sunrise :", "$geoSunrise")
        //weatherTempId.text = tempGeo.toString() + "°C"

        // Changer le .text
        val geoSunset = lat?.let { long?.let { it1 -> weatherViewModel.getGeoTempSunset(it, it1) } }
        Log.d("Sunset :", "$geoSunset")
        //weatherTempId.text = tempGeo.toString() + "°C"

        //Pour l'état du climat
        val ClimatGeo =
            lat?.let { long?.let { it1 -> weatherViewModel.getGeoClimatState(it, it1) } }
        ClimatGeo?.let { Log.d("STATEWEATHER", it) }

        if (ClimatGeo != null) {
            weatherDescriptionId.text = ClimatGeo.uppercase()
        }

        //Pour l'icône du climat
        val iconGeo = lat?.let { long?.let { it1 -> weatherViewModel.getGeoIconURL(it, it1) } }
        iconGeo?.let { Log.d("STATECLIMAT", it) }
        Glide.with(this).load(iconGeo).into(weatherStateId)

        //Pour l'humidité
        val humidityGeo = lat?.let { long?.let { it1 -> weatherViewModel.getGeoHumidity(it, it1) } }
        humidityGeo?.let { Log.d("HUMIDITY", it) }
        weatherHumidityId.text = humidityGeo.toString() + "%"

        //Pour la température ressentie (Penser à faire lier avec les unités)
        var feelslike = lat?.let { long?.let { it1 -> weatherViewModel.getGeoFeels(it, it1) } }
        feelslike?.let { Log.d("RESSENTIE ", it) }
        weatherFeltId.text = feelslike.toString() + " °C"

        //Pour la vitesse du vent
        val windSpeed =
            lat?.let { long?.let { it1 -> weatherViewModel.getGeoWind(it, it1).toString() } }
        windSpeed?.let { Log.d("WIIIIND", it) }
        weatherWindId.text = windSpeed.toString() + " km/h"

        //Pour la qualité de l'air
        weatherQualityId.text =
            lat?.let { long?.let { it1 -> weatherViewModel.getGeoAirQuality(it, it1).toString() } }

        //Notifications à venir en fonction

    }

    private fun searchByCity() {

        weatherSearchId.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                try {
                    //GET TEMPERATURE BY CITY
                    val tempCity = query?.let { weatherViewModel.getCityTemp(it) }
                    weatherTempId.text = "$tempCity°C"

                    //GET TEMPERATURE MIN BY CITY
                    // CHANGER le .text
                    val tempCityMin = query?.let { weatherViewModel.getCityTempMin(it) }
                    // weatherTempId.text = "$tempCityMin°C"
                    //Log.d("Temp_Min", "$tempCityMin°C")

                    //GET TEMPERATURE MIN BY CITY
                    // CHANGER le .text
                    val tempCityMax = query?.let { weatherViewModel.getCityTempMax(it) }
                    // weatherTempId.text = "$tempCityMax°C"
                    //Log.d("Temp_Max", "$tempCityMax°C")

                    //GET SUNRISE BY CITY
                    // CHANGER le .text
                    val sunrise = query?.let { weatherViewModel.getCitySunrise(it) }
                    // weatherTempId.text = "$sunrise"

                    //GET SUNRISE BY CITY
                    // CHANGER le .text
                    val sunset = query?.let { weatherViewModel.getCitySunset(it) }
                    // weatherTempId.text = "$sunset"

                    //GET CITYNAME
                    val cityName = query
                    if (cityName != null) {
                        weatherTitleId.text =
                            cityName.replaceFirstChar { cityName.substring(0, 1).uppercase() }
                    }
                    
                    //GET ICONCLIMAT
                    Glide.with(this@WeatherActivity)
                        .load(query?.let { weatherViewModel.getCityIconClimat(it) })
                        .into(weatherStateId)

                    //GET CLIMAT BY CITY
                    val weatherState = query?.let { weatherViewModel.getCityClimat(it) }
                    if (weatherState != null) {
                        weatherDescriptionId.text = weatherState.uppercase()
                    }
                    //GET FEELSTEMP BY CITY
                    val feelslike = query?.let { weatherViewModel.getCityFeelsTemp(it) }
                    weatherFeltId.text = ((feelslike?.let {
                        BigDecimal(it).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        )
                    }).toString() + "°C")

                    //GET FEELSTEMP BY CITY
                    val humidityCity = query?.let { weatherViewModel.getCityHumidity(it) }
                    if (humidityCity != null) {
                        weatherHumidityId.text = "$humidityCity %"
                    }

                    val windspeedCity = query?.let { weatherViewModel.getCityWind(it) }
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