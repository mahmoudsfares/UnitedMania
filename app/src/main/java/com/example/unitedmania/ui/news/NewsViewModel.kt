package com.example.unitedmania.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unitedmania.pojo.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repo = NewsRepo()
    private val newsChannel = Channel<Boolean>()
    private val newsFlow = newsChannel.receiveAsFlow()

    // cachedIn must be used to prevent the app from crashing when orientation changes, as it cannot load the same data twice.
    val news = newsFlow.flatMapLatest { repo.fetchAllNews().cachedIn(viewModelScope) }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun fetchAllNews(){
        viewModelScope.launch {
            newsChannel.send(true)
        }
    }
}