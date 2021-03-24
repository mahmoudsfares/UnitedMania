package com.example.unitedmania.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unitedmania.R
import com.example.unitedmania.pojo.News

class NewsAdapter(private val context: Context, private val clickedItemPosition: (Int) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>() {

    private lateinit var mNewsList: List<News?>

    fun setData(newsList: List<News?>) {
        mNewsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
        val parentLayout =
            LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return NewsAdapterViewHolder(parentLayout)
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
        val currentNews = mNewsList[position]

        holder.itemView.setOnClickListener {
            clickedItemPosition(holder.adapterPosition)
        }
        holder.mSource.text = currentNews!!.source.name
        holder.mTitle.text = currentNews.title
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    class NewsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mSource: TextView = itemView.findViewById(R.id.news_source)
        var mTitle: TextView = itemView.findViewById(R.id.news_title)
    }
}