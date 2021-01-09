package com.sudhirkhanger.hackernews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudhirkhanger.hackernews.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel internal constructor(
    private val repository: NewsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.newsCount() == 0) {
                fetchNews()
            }
        }
    }

    fun news() = repository.news()

    fun fetchNews() = repository.fetchNews(Dispatchers.IO)
}