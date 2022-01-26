package com.example.weather_project.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.model.WeatherProjectDataBase
import com.example.weather_project.model.entity.UserEntity
import com.example.weather_project.model.repository.UserRepository
import com.example.weather_project.resource.Status
import com.example.weather_project.usecase.UsersUseCase
import com.example.weather_project.viewmodel.signup.SignUpViewModel
import com.example.weather_project.viewmodel.signup.SignUpViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignupActivity : AppCompatActivity() {

    private lateinit var usersVW: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val userDAO = WeatherProjectDataBase.get_Data_Base(application).user_dao()
        val userRepository = UserRepository(userDAO)
        val useCase = UsersUseCase(userRepository)
        val factory = SignUpViewModelFactory(useCase)
        usersVW = ViewModelProvider(this, factory)[SignUpViewModel::class.java]

        clickForSignup()
    }

    private fun clickForSignup() {
        LogupBtnId.setOnClickListener {
            if (validation()) {
                val users = UserEntity(
                    user_name = LogupTxt1Id.text.toString(),
                    user_birthdate = LogupTxt2Id.text.toString(),
                    user_city = "Paris",
                    user_login = LogupTxt3Id.text.toString(),
                    user_password = LogupTxt4Id.text.toString()
                )

                usersVW.insertUserData(users)

                usersVW.insertUserData.observe(this, Observer {
                    when (it.status) {
                        Status.SUCCESS -> {
                            startActivity(Intent(this, SignInActivity::class.java))
                            finish()
                        }

                        else -> {

                        }
                    }
                })
            }
        }
    }

    private fun validation(): Boolean {
        return when {
            LogupTxt1Id.text.isNullOrEmpty() -> {
                false
            }
            LogupTxt3Id.text.isNullOrEmpty() -> {
                false
            }
            LogupTxt4Id.text.isNullOrEmpty() -> {
                false
            }
            else -> {
                true
            }
        }
    }
}
