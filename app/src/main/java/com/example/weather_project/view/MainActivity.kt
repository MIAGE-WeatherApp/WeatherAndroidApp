package com.example.weather_project.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.example.weather_project.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val LOCAL_PERMISSION_ID = 1010

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE)
        val currentDate = sdf.format(Date())
        weatherDateId.text = currentDate

        mainBtnId.setOnClickListener {

            RequestPermission()

            if(!CheckPermission() || !isLocationEnabled()){
                Toast.makeText(this@MainActivity, "Activez la géolocalisation et relancez l'application", Toast.LENGTH_LONG).show()
            }
        }

        //Launch SignIn Activity in the Main One (changer les noms à voir)
        mainSignInId.setOnClickListener {
            val signintempIntent = Intent (this, SignInActivity::class.java)
            startActivity(signintempIntent)
        }
    }

    //Check of permissions (in the manifest, by the users and following the settings)
    fun CheckPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            //Launch WeatherMenu in the Main One
            val weathertempIntent = Intent(this, WeatherActivity::class.java)
            startActivity(weathertempIntent)
            return true
        }
        return false
    }

    fun RequestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCAL_PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}


