package com.example.unitedmania.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unitedmania.pojo.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {


    private val repo = NewsRepo()

    private val loadNewsChannel = Channel<Boolean>()
    private val newsFlow = loadNewsChannel.receiveAsFlow()
    val allNews = newsFlow.flatMapLatest { repo.fetchAllNews() }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    sealed class Resource<T>(val newsList: T? = null, val error: String? = null) {
        class Success<T>(data: T?) : Resource<T>(data)
        class Loading<T> : Resource<T>()
        class Error<T>(error: String) : Resource<T>(error = error)
    }

    fun fetchAllNews() {
        viewModelScope.launch {
            loadNewsChannel.send(true)
        }
    }
}