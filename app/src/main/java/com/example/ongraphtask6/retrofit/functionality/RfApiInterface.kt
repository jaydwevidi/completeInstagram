package com.example.ongraphtask6.retrofit.functionality

import com.example.ongraphtask6.retrofit.models.FeedUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RfApiInterface {

    @Multipart
    @POST(".")
    suspend fun addToFeed (
        @Part ("user_id") uID : RequestBody ,
        @Part ("description") des : RequestBody,
        @Part filePart : MultipartBody.Part  ,
        @Part ("hashtag") hash : RequestBody
    ) : Response<FeedUploadResponse>
}