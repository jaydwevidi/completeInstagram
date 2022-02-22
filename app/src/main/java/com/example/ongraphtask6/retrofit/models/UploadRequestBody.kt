package com.example.ongraphtask6.retrofit.models

data class UploadRequestBody(
    val Image: String,
    val description: String = "jay sample description bla bla bla",
    val hashtag: List<String> = listOf<String>("#jay"),
    val user_id: Int
)