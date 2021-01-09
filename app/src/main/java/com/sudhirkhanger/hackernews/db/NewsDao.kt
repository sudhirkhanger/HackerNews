package com.sudhirkhanger.hackernews.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT COUNT(*) FROM news")
    suspend fun count(): Int

    @Query("SELECT * FROM news")
    fun news(): LiveData<List<Article>>

    @Query("SELECT * FROM news WHERE object_id = :objectId")
    fun newsById(objectId: String): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(articles: List<Article?>?)
}