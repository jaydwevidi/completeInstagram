package com.example.ongraphtask6.retrofit.models.register

import com.example.ongraphtask6.retrofit.models.register.RegisterData

data class RegisterResponse(
    val data: RegisterData,
    val message: String,
    val status: Boolean
)