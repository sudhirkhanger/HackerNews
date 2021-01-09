package com.sudhirkhanger.hackernews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sudhirkhanger.hackernews.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel internal constructor(
    private val repository: NewsRepository
) : ViewModel() {

    init {
        viewModelScope.launch { if (repository.newsSize() == 0) fetchNews() }
    }

    fun getNews() = liveData {
        val articles = repository.getNews()
        emit(articles)
    }

    fun fetchNews() = repository.fetchNews(Dispatchers.IO)
}