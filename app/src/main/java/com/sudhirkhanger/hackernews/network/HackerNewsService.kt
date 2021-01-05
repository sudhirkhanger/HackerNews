package com.sudhirkhanger.hackernews.network

import com.sudhirkhanger.hackernews.db.HackerNewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsService {

    @GET("search")
    suspend fun getNews(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<HackerNewsResponse>

    companion object {
        private const val BASE_URL = "https://hn.algolia.com/api/v1/"

        fun create(): HackerNewsService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HackerNewsService::class.java)
        }
    }
}