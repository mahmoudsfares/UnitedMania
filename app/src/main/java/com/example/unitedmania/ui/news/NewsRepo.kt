package com.example.unitedmania.ui.news

import com.example.unitedmania.pojo.News
import com.example.unitedmania.retrofit.RetrofitClient
import com.example.unitedmania.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class NewsRepo {
    suspend fun fetchAllNews(): List<News>? {
        val service = RetrofitClient.getClient()?.create(RetrofitInterface::class.java)
        val ioDispatcher = Dispatchers.IO
        return withContext(ioDispatcher){
            try {
                val postVisitResponse = service?.getAllNews()
                postVisitResponse?.body()!!.articles
            }
            catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
    }
}