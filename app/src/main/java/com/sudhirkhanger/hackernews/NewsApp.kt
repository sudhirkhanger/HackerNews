package com.sudhirkhanger.hackernews

import android.app.Application
import android.content.Context
import com.sudhirkhanger.hackernews.utilities.CustomDebugTree
import timber.log.Timber

class NewsApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: NewsApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(CustomDebugTree())
    }
}