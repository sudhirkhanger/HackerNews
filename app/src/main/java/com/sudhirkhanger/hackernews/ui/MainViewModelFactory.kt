package com.sudhirkhanger.hackernews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sudhirkhanger.hackernews.HackerNewsRepository

class MainViewModelFactory(
    private val repository: HackerNewsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}