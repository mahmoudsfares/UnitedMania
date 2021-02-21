package com.example.unitedmania.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitedmania.pojo.News

class NewsViewModel: ViewModel() {

    private val repo = NewsRepo()
    private val news = MutableLiveData<List<News?>?>()

    fun fetchAllNews(){
        repo.fetchAllNews(news)
    }

    fun getNews(): MutableLiveData<List<News?>?>{
        return news
    }
}