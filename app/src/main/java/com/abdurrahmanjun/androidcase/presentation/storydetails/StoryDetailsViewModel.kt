package com.abdurrahmanjun.androidcase.presentation.storydetails

import android.app.Application
import androidx.lifecycle.*
import com.abdurrahmanjun.androidcase.business.datasource.cache.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.Favorite
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteRepository
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsCommentResult
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.domain.interactor.GetStoryDetails
import com.abdurrahmanjun.androidcase.business.domain.models.Comment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class StoryDetailsViewModel (application: Application) :AndroidViewModel(application) {

    private val repository: FavoriteRepository
    var storyId: Int = 0
    val disposables = CompositeDisposable()
    val getStoryDetails: GetStoryDetails = GetStoryDetails()

    // boolean favorite
    private var _booleanFavoriteDataState: MutableLiveData<Boolean> = MutableLiveData()
    val booleanFavoriteDataState: LiveData<Boolean>
        get() = _booleanFavoriteDataState

    init {
        val dao = AndroidCaseDatabase.getInstance(application).favoriteDao
        repository = FavoriteRepository(dao)
    }

    fun isFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            _booleanFavoriteDataState.postValue(repository.loadId(storyId) != null)
        }
    }

    fun favoriteAction(title : String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!_booleanFavoriteDataState.value!!) {
                repository.insert(Favorite(storyId,title))
                _booleanFavoriteDataState.postValue(true)
            } else {
                repository.delete(storyId)
                _booleanFavoriteDataState.postValue(false)
            }
        }
    }

}