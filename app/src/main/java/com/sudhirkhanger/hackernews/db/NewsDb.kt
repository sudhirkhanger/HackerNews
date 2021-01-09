package com.sudhirkhanger.hackernews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sudhirkhanger.hackernews.utilities.NEWS_DB

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDb : RoomDatabase() {

    abstract fun hackerNewsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDb? = null

        fun getDatabase(context: Context): NewsDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDb::class.java,
                    NEWS_DB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}