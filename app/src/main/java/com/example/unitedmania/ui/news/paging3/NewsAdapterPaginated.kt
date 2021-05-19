package com.example.unitedmania.ui.news.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unitedmania.databinding.NewsListItemBinding
import com.example.unitedmania.pojo.News


/**
 * displayed data is provided in type PagingData<T>, T being the type of the object that populates one list item, not a list of that type
 * data is provided to its final function submitData(lifecycle, PagingData<T>)
 */
class NewsAdapterPaginated(private val onItemClicked: (News) -> Unit) :
    PagingDataAdapter<News, NewsAdapterPaginated.NewsViewHolder>(NEWS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
    }

    inner class NewsViewHolder(private val binding: NewsListItemBinding, private val onNewsItemClicked: (News) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                // to avoid a crash if u click on an item while it is animating off the screen
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = getItem(bindingAdapterPosition)
                    if (clickedItem != null) {
                        onNewsItemClicked(clickedItem)
                    }
                }
            }
        }

        fun bind(news: News) {
            binding.apply {
                newsSource.text = news.source.name
                newsTitle.text = news.title
            }
        }
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.url == newItem.url
            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
        }
    }
}