package com.sudhirkhanger.hackernews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.hackernews.databinding.ItemArticleBinding
import com.sudhirkhanger.hackernews.db.HitsItem

class NewsAdapter(private val onClick: (String?) -> Unit) :
    ListAdapter<HitsItem, NewsAdapter.NewsViewHolder>(NewsDiffUtil()) {

    class NewsDiffUtil : DiffUtil.ItemCallback<HitsItem>() {
        override fun areItemsTheSame(oldItem: HitsItem, newItem: HitsItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HitsItem, newItem: HitsItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class NewsViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: HitsItem?) {
            binding.textView.text = article?.title
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}