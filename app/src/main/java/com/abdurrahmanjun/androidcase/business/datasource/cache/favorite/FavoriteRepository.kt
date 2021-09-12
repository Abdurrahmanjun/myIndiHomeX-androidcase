package com.abdurrahmanjun.androidcase.business.datasource.cache.favorite

import androidx.lifecycle.LiveData

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun allNotes() : List<Favorite> = favoriteDao.getAll()
    suspend fun insert(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun delete(id: Int) = favoriteDao.deleteByStoryId(id)
}