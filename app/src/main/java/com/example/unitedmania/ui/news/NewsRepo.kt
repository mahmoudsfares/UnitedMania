package com.example.unitedmania.ui.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unitedmania.retrofit.RetrofitClient
import com.example.unitedmania.retrofit.RetrofitInterface
import com.example.unitedmania.ui.news.paging3.NewsPagingSource

private const val PAGE_SIZE = 10
private const val MAX_SIZE = 30

class NewsRepo {

    private val service = RetrofitClient.getClient()?.create(RetrofitInterface::class.java)

    fun fetchAllNews() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, maxSize = MAX_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { NewsPagingSource(service!!) }
    ).flow
}