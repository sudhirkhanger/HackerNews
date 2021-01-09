package com.sudhirkhanger.hackernews

import android.content.Context
import android.content.SharedPreferences
import com.sudhirkhanger.hackernews.db.NewsDb
import com.sudhirkhanger.hackernews.network.NewsService
import com.sudhirkhanger.hackernews.ui.MainViewModelFactory
import com.sudhirkhanger.hackernews.utilities.SHARED_PREF_FILE

object NewsComponent {

    private fun hackerNewsDb() = NewsDb.getDatabase(NewsApp.applicationContext())

    private fun hackerNewsRepository() =
        NewsRepository.getInstance(NewsService.create(), hackerNewsDb().hackerNewsDao())

    fun provideMainViewModelFactory(): MainViewModelFactory =
        MainViewModelFactory(hackerNewsRepository())

    fun sharedPreference(): SharedPreferences? =
        NewsApp.applicationContext()
            .getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
}