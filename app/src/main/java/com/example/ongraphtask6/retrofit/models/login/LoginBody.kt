package com.example.ongraphtask6.retrofit.models.login

data class LoginBody(
    val device_token: String,
    val email: String,
    val password: String
)