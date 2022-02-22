package com.example.ongraphtask6.retrofit.functionality

import com.example.ongraphtask6.retrofit.models.login.LoginBody
import com.example.ongraphtask6.retrofit.models.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InstagramAPI {

    @POST("login")
    suspend fun login ( @Body loginDetails : LoginBody) : Response<LoginResponse>
}