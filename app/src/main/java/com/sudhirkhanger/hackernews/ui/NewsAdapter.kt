package com.sudhirkhanger.hackernews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.hackernews.databinding.ItemArticleBinding
import com.sudhirkhanger.hackernews.db.HitsItem
import timber.log.Timber

class NewsAdapter(private val articles: MutableList<HitsItem?>?) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles?.get(position))
    }

    override fun getItemCount(): Int = articles?.size ?: 0

    inner class NewsViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: HitsItem?) {
            binding.textView.text = article?.title
        }
    }

    fun submitList(articleList: List<HitsItem>) {
        articles?.addAll(articleList)
        notifyDataSetChanged()
    }
}