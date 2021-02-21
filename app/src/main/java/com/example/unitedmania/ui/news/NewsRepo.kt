package com.example.unitedmania.ui.news

import androidx.lifecycle.MutableLiveData
import com.example.unitedmania.pojo.News
import com.example.unitedmania.pojo.NewsResponse
import com.example.unitedmania.retrofit.RetrofitClient
import com.example.unitedmania.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NewsRepo {
    fun fetchAllNews(news: MutableLiveData<List<News?>?>) {
        val service = RetrofitClient.getClient()?.create(RetrofitInterface::class.java)
        val postVisitCall = service?.getAllNews()
        postVisitCall?.enqueue(object : Callback<NewsResponse?> {
            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
                val res = response.body()!!.articles
                news.postValue(res)
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}