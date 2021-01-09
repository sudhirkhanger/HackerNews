package com.sudhirkhanger.hackernews.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT COUNT(*) FROM news")
    suspend fun newsSize(): Int

    @Query("SELECT * FROM news")
    suspend fun newsItems(): List<Article>

    @Query("SELECT * FROM news WHERE object_id = :objectId")
    suspend fun newsArticleById(objectId: String): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticles(articles: List<Article?>?)
}