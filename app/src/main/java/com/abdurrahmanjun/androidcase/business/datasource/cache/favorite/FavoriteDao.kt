package com.abdurrahmanjun.androidcase.business.datasource.cache.favorite

import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Transaction
    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE story_id = :story_id")
    suspend fun loadId(story_id: Int): Favorite

    @Query("DELETE FROM favorite WHERE story_id = :story_id")
    suspend fun deleteByStoryId(story_id: Int)
}