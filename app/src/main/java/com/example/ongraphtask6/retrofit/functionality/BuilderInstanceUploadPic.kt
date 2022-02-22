package com.example.uploadfeed.retrofit

import com.example.ongraphtask6.retrofit.functionality.RfApiInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BuilderInstanceUploadPic {
    private val baseURL = "http://demo2.ongraph.com/demo/pixprt/api/UserFeed/add_userfeed/"

    val gson = GsonBuilder().setLenient().create()
    private val myBuilder by lazy {
        Retrofit
            .Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val builderAPI by lazy {
        myBuilder.create(RfApiInterface::class.java)
    }
}