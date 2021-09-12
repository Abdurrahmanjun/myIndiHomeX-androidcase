package com.abdurrahmanjun.androidcase.business.datasource.cache.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "story_title") val storyTitle: String?,
    @ColumnInfo(name = "story_id") val storyId: Int,
){
    constructor(storyId: Int, storyTitle: String) :
            this(0, storyTitle, storyId)
}