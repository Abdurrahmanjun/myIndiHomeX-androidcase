package com.abdurrahmanjun.androidcase.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "story_title") val storyTitle: String?,
    @ColumnInfo(name = "story_id") val storyId: String?,
)