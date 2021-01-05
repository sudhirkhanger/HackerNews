package com.sudhirkhanger.hackernews.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hitsitem")
    suspend fun hackerNewsItems(): List<HitsItem>

    @Query("SELECT * FROM hitsitem WHERE object_id = :objectId")
    suspend fun hackerNewsArticleById(objectId: String): HitsItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticles(articles: List<HitsItem?>?)
}