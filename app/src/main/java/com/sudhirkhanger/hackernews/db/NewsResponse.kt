package com.sudhirkhanger.hackernews.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HackerNewsResponse(

    @field:SerializedName("hits")
    val articles: List<Article?>? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("nbPages")
    val nbPages: Int? = null,
)

@Entity(tableName = "news")
data class Article(

    @field:SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "object_id")
    @NonNull
    @field:SerializedName("objectID")
    val objectID: String
)
