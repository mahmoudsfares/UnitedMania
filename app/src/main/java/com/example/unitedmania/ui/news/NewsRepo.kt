package com.example.unitedmania.ui.news

import androidx.lifecycle.MutableLiveData
import com.example.unitedmania.pojo.News
import com.example.unitedmania.pojo.NewsResponse
import com.example.unitedmania.retrofit.RetrofitClient
import com.example.unitedmania.retrofit.RetrofitInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.util.*

class NewsRepo {
    fun fetchAllNews(news: MutableLiveData<List<News?>?>) {
        val service = RetrofitClient.getClient()?.create(RetrofitInterface::class.java)
        GlobalScope.launch {
            val postVisitCall = service?.getAllNews()?.await()
            news.postValue(postVisitCall!!.articles)
        }
    }
}