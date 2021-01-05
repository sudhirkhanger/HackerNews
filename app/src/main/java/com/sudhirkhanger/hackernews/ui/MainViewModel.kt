package com.sudhirkhanger.hackernews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sudhirkhanger.hackernews.HackerNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel internal constructor(
    private val repository: HackerNewsRepository
) : ViewModel() {

    init {
        viewModelScope.launch { if (repository.getAllNews().isEmpty()) fetchNews() }
    }

    fun getNews() = liveData {
        val articles = repository.getAllNews()
        emit(articles)
    }

    fun fetchNews() = repository.fetchNews(Dispatchers.IO)
}