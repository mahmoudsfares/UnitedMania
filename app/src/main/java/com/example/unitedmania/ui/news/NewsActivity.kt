package com.example.unitedmania.ui.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unitedmania.databinding.ActivityNewsBinding
import com.example.unitedmania.ui.details.DetailsActivity
import kotlinx.coroutines.flow.collect

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel

    private lateinit var adapter: NewsAdapter
    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.fetchAllNews()

        supportActionBar!!.title = "Latest News"

        adapter = NewsAdapter(applicationContext, clickedItemPosition = { position -> onNewsItemSelected(position) })
        manager = LinearLayoutManager(this)

        binding.refresher.setOnRefreshListener {
            viewModel.fetchAllNews()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.newsState.collect {
                when (it) {
                    is NewsViewModel.NewsState.RetrievedArticles -> {
                        if (it.newsList != null && it.newsList.isNotEmpty()) {
                            adapter.setData(it.newsList)
                            binding.newsRv.adapter = adapter
                            binding.newsRv.layoutManager = manager
                            binding.refresher.isRefreshing = false
                            binding.newsRv.visibility = View.VISIBLE
                            binding.noResults.visibility = View.GONE
                            binding.loadingSpinner.visibility = View.GONE
                        } else {
                            binding.refresher.isRefreshing = false
                            binding.noResults.visibility = View.VISIBLE
                            binding.newsRv.visibility = View.GONE
                            binding.loadingSpinner.visibility = View.GONE
                        }
                    }
                    else -> {
                        binding.noResults.visibility = View.GONE
                        binding.newsRv.visibility = View.GONE
                        binding.loadingSpinner.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun onNewsItemSelected(position: Int){
        val news = viewModel.newsState.value
        if (news is NewsViewModel.NewsState.RetrievedArticles) {
            val clickedNews = news.newsList!![position]
            val toDetails = Intent(this@NewsActivity, DetailsActivity::class.java)
            toDetails.putExtra("source", clickedNews.source.name)
            toDetails.putExtra("title", clickedNews.title)
            toDetails.putExtra("details", clickedNews.details)
            toDetails.putExtra("url", clickedNews.url)
            toDetails.putExtra("imageUrl", clickedNews.imageUrl)
            startActivity(toDetails)
        }
    }
}