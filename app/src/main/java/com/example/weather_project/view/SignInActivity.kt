package com.example.weather_project.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather_project.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.model.WeatherProjectDataBase
import com.example.weather_project.model.repository.UserRepository
import com.example.weather_project.resource.Status
import com.example.weather_project.usecase.UsersUseCase
import com.example.weather_project.viewmodel.signin.SignInViewModel
import com.example.weather_project.viewmodel.signin.SignInViewModelFactory


class SignInActivity : AppCompatActivity() {

    private lateinit var usersVW: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val userDAO = WeatherProjectDataBase.get_Data_Base(application).user_dao()
        val userRepository = UserRepository(userDAO)
        val usersUseCase = UsersUseCase(userRepository)
        val factory = SignInViewModelFactory(usersUseCase)
        usersVW = ViewModelProvider(this, factory)[SignInViewModel::class.java]

        onLoginClick()

        onSignUpClick()
    }

    private fun onSignUpClick() {
        LogSignupId.setOnClickListener {
            val signupIntent = Intent(this, SignupActivity::class.java)
            startActivity(signupIntent)
        }
    }

    private fun onLoginClick() {
        LogBtnId.setOnClickListener {
            apply {
                setUpViewModel()
            }
        }
    }

    private fun setUpViewModel() {

        usersVW.getUserLoginDataStatus(
            LogEmailId.text.toString(),
            LogPwdId.text.toString()
        )

        usersVW.getUserLoginDataStatus.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        startActivity(Intent(this, WeatherActivity::class.java))
                        
                        createNotificationChannel(it.data.user_name)
                        
                        Toast.makeText(
                            this,
                            "HELLO " + it.data.user_name + " !!!",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(this, "User does not exist in database.", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                else -> {

                }
            }
        })
    }

    private fun createNotificationChannel(username:String){

        lateinit var notificationChannel: NotificationChannel
        lateinit var notificationManager: NotificationManager
        lateinit var builder: Notification.Builder
        val channelId ="com.example.weather_project.view"
        val description = "SARAAAAAAAH"
        val usersname = username.replaceFirstChar{username.substring(0,1).uppercase()}

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, WeatherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            notificationChannel = NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("Bienvenue dans WEATH'APP $usersname!!")
                .setContentText("Consultez la météo et pleins d'autres informations")
                .setSmallIcon((R.mipmap.ic_launcher_round))
                .setLargeIcon((BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher)))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }
}