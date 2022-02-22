package com.example.ongraphtask6.retrofit.functionality
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ViewFeedBuilderInstance {

    private val baseURL = "http://demo2.ongraph.com/demo/pixprt/api/UserFeed/view_userfeed/"


    val gson = GsonBuilder().setLenient().create()
    private val myBuilder by lazy {
        Retrofit
            .Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val builderAPI by lazy {
        myBuilder.create(GetFeedInterface::class.java)
    }
}
