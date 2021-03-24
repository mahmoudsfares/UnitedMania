package com.example.unitedmania.ui.news

import androidx.lifecycle.ViewModel
import com.example.unitedmania.pojo.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val job = Job()
    private val mainDispatcher = Dispatchers.Main
    private val uiScope = CoroutineScope(mainDispatcher + job)

    private val repo = NewsRepo()

    private val _newsState = MutableStateFlow<NewsState>(NewsState.Default)
    val newsState: StateFlow<NewsState>
        get() = _newsState

    sealed class NewsState {
        data class RetrievedArticles(val newsList: List<News>?) : NewsState()
        object Default : NewsState()
    }

    fun fetchAllNews() {
        _newsState.value = NewsState.Default
        uiScope.launch {
            _newsState.value = NewsState.RetrievedArticles(repo.fetchAllNews())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}