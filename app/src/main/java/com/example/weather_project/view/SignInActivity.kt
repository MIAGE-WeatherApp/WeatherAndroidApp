package com.example.weather_project.view

import android.content.Intent
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
                        startActivity(Intent(this, Weather_Display::class.java))
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
}