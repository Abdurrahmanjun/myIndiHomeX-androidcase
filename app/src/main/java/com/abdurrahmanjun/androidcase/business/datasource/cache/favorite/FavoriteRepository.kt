package com.abdurrahmanjun.androidcase.business.datasource.cache.favorite

import androidx.lifecycle.LiveData

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun getAll() : List<Favorite> = favoriteDao.getAll()
    suspend fun loadId(story_id: Int): Favorite = favoriteDao.loadId(story_id)
    suspend fun insert(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun delete(id: Int) = favoriteDao.deleteByStoryId(id)
}