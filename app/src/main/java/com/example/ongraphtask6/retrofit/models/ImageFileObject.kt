package com.example.ongraphtask6.retrofit.models

import java.io.File

data class ImageFileObject(
    val title : String ,
    val path : String ,
    //val bitmap: Bitmap,
    val file : File
)
