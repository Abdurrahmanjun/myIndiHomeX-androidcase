package com.abdurrahmanjun.androidcase.presentation.storydetails

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteDao
import com.abdurrahmanjun.androidcase.business.domain.interactor.GetStoryDetails
import io.reactivex.disposables.CompositeDisposable

class StoryDetailsViewModel : ViewModel() {

    private lateinit var dao: FavoriteDao

    private val disposables = CompositeDisposable()
    private var flagFav : Boolean = false
    var storyId: Int = 0

    val getStoryDetails: GetStoryDetails = GetStoryDetails()
}