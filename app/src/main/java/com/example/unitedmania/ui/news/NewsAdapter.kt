package com.example.unitedmania.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.unitedmania.R
import com.example.unitedmania.pojo.News

class NewsAdapter(private val context: Context): RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>() {

    private lateinit var mNewsList: List<News?>
    private lateinit var mClickedItemPosition: MutableLiveData<Int>

    fun setData(newsList: List<News?>, clickedItemPosition: MutableLiveData<Int>){
        mNewsList = newsList
        this.mClickedItemPosition = clickedItemPosition
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
        val parentLayout = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return NewsAdapterViewHolder(parentLayout)
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
        val currentNews = mNewsList[position]
        holder.itemView.setOnClickListener {
            mClickedItemPosition.value = holder.adapterPosition
        }
        holder.mSource.text = currentNews!!.source.name
        holder.mTitle.text = currentNews.title
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    class NewsAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mSource: TextView = itemView.findViewById(R.id.news_source)
        var mTitle: TextView = itemView.findViewById(R.id.news_title)
    }
}