package com.example.weather_project.viewmodel.weather.weathercomponent

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*


class Location(
    @NonNull
    @Size(min = 2, max = 255)
    private var _city: String
) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var countryName: String
    private lateinit var geoCoder: Geocoder
    private lateinit var address: List<Address>

    init {
        this._city
    }

    private fun getCityName(
        lat: Double,
        long: Double,
        appCompatActivity: AppCompatActivity
    ): String {
        var cityName = this._city
        countryName = ""
        geoCoder = Geocoder(appCompatActivity, Locale.getDefault()) // Avoir pour le This
        address = geoCoder.getFromLocation(lat, long, 3)

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
    fun newLocationData(appCompatActivity: AppCompatActivity) {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(appCompatActivity)
    }

    fun getCity() : String {
        return this._city
    }

}