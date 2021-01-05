package com.sudhirkhanger.hackernews

import android.app.Application
import android.content.Context
import android.util.Log
import com.sudhirkhanger.hackernews.utilities.CustomDebugTree
import timber.log.Timber

class HackerNewsApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: HackerNewsApp? = null

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