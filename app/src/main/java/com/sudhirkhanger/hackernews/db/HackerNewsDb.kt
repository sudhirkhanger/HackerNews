package com.sudhirkhanger.hackernews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sudhirkhanger.hackernews.utilities.NEWS_DB

@Database(entities = [HitsItem::class], version = 1, exportSchema = false)
abstract class HackerNewsDb : RoomDatabase() {

    abstract fun hackerNewsDao(): HackerNewsDao

    companion object {
        @Volatile
        private var INSTANCE: HackerNewsDb? = null

        fun getDatabase(context: Context): HackerNewsDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HackerNewsDb::class.java,
                    NEWS_DB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}