package com.example.unitedmania.pojo

import com.google.gson.annotations.SerializedName

data class NewsResponse(@SerializedName ("articles") val articles: List<News>)