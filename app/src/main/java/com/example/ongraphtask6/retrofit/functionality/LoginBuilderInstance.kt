package com.example.ongraphtask6.retrofit.functionality

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginBuilderInstance {
    private val baseURL = "http://demo2.ongraph.com/demo/pixprt/api/authentication/"

    private val myBuilder by lazy {
        Retrofit
            .Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val builderAPI by lazy {
        myBuilder.create(InstagramAPI::class.java)
    }
}