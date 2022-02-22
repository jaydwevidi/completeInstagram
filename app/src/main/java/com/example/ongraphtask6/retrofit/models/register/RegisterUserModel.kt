package com.example.ongraphtask6.retrofit.models.register

data class RegisterUserModel(
    val device_token: String = "wife8789789",
    val email: String ,
    val first_name: String = "Jay",
    val last_name: String = "Dwivedi",
    val password: String ,
    val user_name: String ,
    val confirm_password: String = password,
)