package com.example.unitedmania.ui.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unitedmania.databinding.ActivityNewsBinding
import com.example.unitedmania.pojo.News
import com.example.unitedmania.ui.details.DetailsActivity
import com.example.unitedmania.ui.news.paging3.PagingLoadStateAdapter
import com.example.unitedmania.ui.news.paging3.NewsAdapterPaginated
import kotlinx.coroutines.flow.collect

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        supportActionBar!!.title = "Latest News"
        manager = LinearLayoutManager(this)

        if(viewModel.news.value == null)
            viewModel.fetchAllNews()

        val pagingAdapter = NewsAdapterPaginated(onItemClicked = { onNewsItemSelected(it) })
        val loadStateHeader = PagingLoadStateAdapter { pagingAdapter.retry() }
        val loadStateFooter = PagingLoadStateAdapter { pagingAdapter.retry() }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadingSpinner.isVisible = loadState.source.refresh is LoadState.Loading
                newsRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                noResults.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

        binding.newsRv.apply {
            layoutManager = manager
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(header = loadStateHeader, footer = loadStateFooter)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.news.collect {
                binding.refresher.isRefreshing = false
                pagingAdapter.submitData(lifecycle, it!!)
            }
        }

        binding.refresher.setOnRefreshListener {
            binding.refresher.isRefreshing = true
            viewModel.fetchAllNews()
        }
    }

    private fun onNewsItemSelected(clickedNews: News){
        val toDetails = Intent(this@NewsActivity, DetailsActivity::class.java)
        toDetails.putExtra("source", clickedNews.source.name)
        toDetails.putExtra("title", clickedNews.title)
        toDetails.putExtra("details", clickedNews.details)
        toDetails.putExtra("url", clickedNews.url)
        toDetails.putExtra("imageUrl", clickedNews.imageUrl)
        startActivity(toDetails)
    }

}