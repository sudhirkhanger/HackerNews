package com.sudhirkhanger.hackernews

import com.sudhirkhanger.hackernews.db.NewsDao
import com.sudhirkhanger.hackernews.network.NewsService
import com.sudhirkhanger.hackernews.utilities.DEFAULT_QUERY
import com.sudhirkhanger.hackernews.utilities.NB_PAGE_COUNT
import com.sudhirkhanger.hackernews.utilities.PAGE_COUNT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class NewsRepository private constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
) {

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(newsService: NewsService, articleDao: NewsDao) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: NewsRepository(newsService, articleDao)
                            .also { instance = it }
                }
    }

    fun news() = newsDao.news()
    suspend fun newsCount() = newsDao.count()

    fun fetchNews(coroutineContext: CoroutineContext) {
        val nbPageCount = NewsComponent.sharedPreference()?.getInt(NB_PAGE_COUNT, -1) ?: -1
        val pageCount = NewsComponent.sharedPreference()?.getInt(PAGE_COUNT, 0) ?: 0
        if (nbPageCount != 0) fetchNewsByPage(coroutineContext, pageCount)
    }

    private fun fetchNewsByPage(coroutineContext: CoroutineContext, pageRequested: Int) {
        CoroutineScope(coroutineContext).launch {
            try {
                val newsResponse = newsService.getNews(DEFAULT_QUERY, pageRequested)
                val body = newsResponse.body()
                if (newsResponse.isSuccessful && body != null)
                    newsDao.insertNews(body.articles)
            } catch (e: Exception) {
                Timber.e(e, "Some error occurred")
            }
        }
    }
}