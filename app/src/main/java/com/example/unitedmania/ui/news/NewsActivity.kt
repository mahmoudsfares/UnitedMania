package com.example.unitedmania.ui.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unitedmania.databinding.ActivityNewsBinding
import com.example.unitedmania.ui.details.DetailsActivity

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    private val clickedItemPosition = MutableLiveData<Int>()
    private lateinit var adapter: NewsAdapter
    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.fetchAllNews()

        supportActionBar!!.title = "Latest News"

        adapter = NewsAdapter(applicationContext)
        manager = LinearLayoutManager(this)

        viewModel.getNews().observe(this, {
            adapter.setData(it!!, clickedItemPosition)
            binding.newsRv.adapter = adapter
            binding.newsRv.layoutManager = manager
        })

        clickedItemPosition.observe(this, {
            val clickedNews = viewModel.getNews().value?.get(it)
            val toDetails = Intent(this, DetailsActivity::class.java)
            toDetails.putExtra("source", clickedNews?.source?.name)
            toDetails.putExtra("title", clickedNews?.title)
            toDetails.putExtra("details", clickedNews?.details)
            toDetails.putExtra("url", clickedNews?.url)
            toDetails.putExtra("imageUrl", clickedNews?.imageUrl)
            startActivity(toDetails)
        })
    }
}