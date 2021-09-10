package com.abdurrahmanjun.androidcase.data.database

import androidx.room.*
import com.abdurrahmanjun.androidcase.data.database.model.Favorite

@Dao
interface AndroidCaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Transaction
    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<Favorite>

    @Delete
    suspend fun delete(favorite: Favorite)
}