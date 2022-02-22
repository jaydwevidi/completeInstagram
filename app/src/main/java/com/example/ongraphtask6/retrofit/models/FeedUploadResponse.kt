package com.example.ongraphtask6.retrofit.models

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

data class FeedUploadResponse(
    val `data`: Int,
    val message: String,
    val status: String
)
