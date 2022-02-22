package com.example.ongraphtask6.retrofit.functionality

import com.example.ongraphtask6.retrofit.models.view_feed.ViewFeedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface GetFeedInterface {


    @POST(".")
    suspend fun addToFeed (
        @Body userID : UserIDToSend
    ) : Response<ViewFeedResponse>
}