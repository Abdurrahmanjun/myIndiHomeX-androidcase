package com.abdurrahmanjun.androidcase.presentation.topstory

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.abdurrahmanjun.androidcase.business.datasource.cache.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.Favorite
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteDao
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.domain.interactor.GetTopStory
import com.abdurrahmanjun.androidcase.business.domain.models.Story
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class TopStoryViewModel (application: Application) : AndroidViewModel(application) {

    private var dao: FavoriteDao
    val disposables = CompositeDisposable()
    val getTopStory: GetTopStory = GetTopStory()

    // boolean favorite
    private var _listFavoriteDataState: MutableLiveData<List<Favorite>> = MutableLiveData()
    val listFavoriteDataState: LiveData<List<Favorite>>
        get() = _listFavoriteDataState

    init {
        dao = AndroidCaseDatabase.getInstance(application).favoriteDao
    }

    fun populateFavoriteStory() {
        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getAll() != null) {
                _listFavoriteDataState.postValue(dao.getAll())
                Log.d("FavoriteStories", _listFavoriteDataState.value.toString())
            }
        }
    }
}