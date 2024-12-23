package com.example.snowboard.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboard.Lists.NewsList
import com.example.snowboard.R

class NewsAdapter(private val newsList: ArrayList<NewsList>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_news,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textName.text = currentItem.title
        holder.textComment.text = currentItem.comment
        holder.textNewsDate.text = currentItem.newsDate
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_news)
        val textName: TextView = itemView.findViewById(R.id.title)
        val textComment: TextView = itemView.findViewById(R.id.comment)
        val textNewsDate: TextView = itemView.findViewById(R.id.news_date)
    }
}