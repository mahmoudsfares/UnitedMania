package com.example.unitedmania.ui.news

import com.example.unitedmania.pojo.News
import com.example.unitedmania.retrofit.RetrofitClient
import com.example.unitedmania.retrofit.RetrofitInterface
import com.example.unitedmania.ui.news.NewsViewModel.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepo {

    private val service = RetrofitClient.getClient()?.create(RetrofitInterface::class.java)

    fun fetchAllNews(): Flow<Resource<List<News>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(service!!.getAllNews().body()!!.articles))
        }
        catch (throwable: Throwable) {
            when (throwable) {
                is HttpException ->
                    emit(Resource.Error<List<News>>("Server error"))
                is IOException ->
                    emit(Resource.Error<List<News>>("No internet connection"))
                else ->
                    emit(Resource.Error<List<News>>(throwable.localizedMessage ?: "Error loading data"))
            }
        }
    }
}