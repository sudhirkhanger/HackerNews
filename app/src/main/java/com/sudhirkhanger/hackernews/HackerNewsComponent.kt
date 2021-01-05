package com.sudhirkhanger.hackernews

import android.content.Context
import android.content.SharedPreferences
import com.sudhirkhanger.hackernews.db.HackerNewsDb
import com.sudhirkhanger.hackernews.network.HackerNewsService
import com.sudhirkhanger.hackernews.ui.MainViewModelFactory
import com.sudhirkhanger.hackernews.utilities.SHARED_PREF_FILE

object HackerNewsComponent {

    private fun hackerNewsDb() = HackerNewsDb.getDatabase(HackerNewsApp.applicationContext())

    private fun hackerNewsRepository() =
        HackerNewsRepository.getInstance(HackerNewsService.create(), hackerNewsDb().hackerNewsDao())

    fun provideMainViewModelFactory(): MainViewModelFactory =
        MainViewModelFactory(hackerNewsRepository())

    fun sharedPreference(): SharedPreferences? =
        HackerNewsApp.applicationContext()
            .getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
}