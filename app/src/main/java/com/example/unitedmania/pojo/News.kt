package com.example.unitedmania.pojo

import com.google.gson.annotations.SerializedName

data class News(@SerializedName ("source") val source: Source,
                @SerializedName ("title") val title: String,
                @SerializedName ("content") val details: String,
                @SerializedName ("url") val url: String){

    @SerializedName("urlToImage") val imageUrl: String = ""
}