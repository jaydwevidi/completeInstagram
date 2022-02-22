package com.example.ongraphtask6.retrofit.models.login

data class LoginResponseDataX(
        val active: String,
        val authcode: String,
        val created_on: String,
        val device_token: String,
        val email: String,
        val id: String,
        val last_login: String,
        val mobile_no: String,
        val password: String,
        val profile_pic: String,
        val stripe_customer_id: Any,
        val token: String,
        val user_name: String
    )