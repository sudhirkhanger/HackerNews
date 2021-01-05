package com.sudhirkhanger.hackernews

import android.util.Log
import com.sudhirkhanger.hackernews.db.HackerNewsDao
import com.sudhirkhanger.hackernews.network.HackerNewsService
import com.sudhirkhanger.hackernews.utilities.DEFAULT_QUERY
import com.sudhirkhanger.hackernews.utilities.NB_PAGE_COUNT
import com.sudhirkhanger.hackernews.utilities.PAGE_COUNT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class HackerNewsRepository private constructor(
    private val hackerNewsService: HackerNewsService,
    private val articleDao: HackerNewsDao
) {

    companion object {
        @Volatile
        private var instance: HackerNewsRepository? = null

        fun getInstance(hackerNewsService: HackerNewsService, articleDao: HackerNewsDao) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: HackerNewsRepository(hackerNewsService, articleDao)
                            .also { instance = it }
                }
    }

    suspend fun getAllNews() = articleDao.hackerNewsItems()

    fun fetchNews(coroutineContext: CoroutineContext) {
        val nbPageCount = HackerNewsComponent.sharedPreference()?.getInt(NB_PAGE_COUNT, -1) ?: -1
        val pageCount = HackerNewsComponent.sharedPreference()?.getInt(PAGE_COUNT, 0) ?: 0
        if (nbPageCount != 0) fetchNewsByPage(coroutineContext, pageCount)
    }

    private fun fetchNewsByPage(coroutineContext: CoroutineContext, pageRequested: Int) {
        CoroutineScope(coroutineContext).launch {
            try {
                val newsResponse = hackerNewsService.getNews(DEFAULT_QUERY, pageRequested)
                val body = newsResponse.body()
                if (newsResponse.isSuccessful && body != null)
                    articleDao.insertNewsArticles(body.hits)
            } catch (e: Exception) {
                Timber.e(e, "Some error occurred")
            }
        }
    }
}