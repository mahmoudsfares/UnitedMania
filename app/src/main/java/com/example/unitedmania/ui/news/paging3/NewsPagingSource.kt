package com.example.unitedmania.ui.news.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unitedmania.pojo.News
import com.example.unitedmania.retrofit.RetrofitInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource(private val service: RetrofitInterface) : PagingSource<Int, News>() {

    // responsible for loading data from API using Retrofit
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        // key will be null at the very beginning, so we set it to 1
        val currentPosition = params.key ?: STARTING_PAGE_INDEX
        return try {
            val articles = service.getAllNews(currentPosition, params.loadSize).body()!!.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (currentPosition == STARTING_PAGE_INDEX) null else currentPosition - 1,
                nextKey = if (articles.isEmpty()) null else currentPosition + 1
            )
        } catch (exception: IOException) { // no internet connection
            LoadResult.Error(exception)
        } catch (exception: HttpException) { // server errors
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        TODO("Not yet implemented")
    }
}