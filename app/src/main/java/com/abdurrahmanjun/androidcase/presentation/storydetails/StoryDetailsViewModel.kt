package com.abdurrahmanjun.androidcase.presentation.storydetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.abdurrahmanjun.androidcase.business.datasource.cache.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.Favorite
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteDao
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

    private var dao: FavoriteDao
    var storyId: Int = 0
    val disposables = CompositeDisposable()
    val getStoryDetails: GetStoryDetails = GetStoryDetails()
    
    // boolean favorite
    private var _booleanFavoriteDataState: MutableLiveData<Boolean> = MutableLiveData()
    val booleanFavoriteDataState: LiveData<Boolean>
        get() = _booleanFavoriteDataState

    init {
        dao = AndroidCaseDatabase.getInstance(application).favoriteDao
    }

    fun getDetailsStoryObservable(): Observable<StoryDetailsResult>? {
        return ServiceGenerator.getRequestApi()
            .getStoryDetails(storyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCommentsDetailsObservable(commentId: Int): Observable<Comment> {
        return commentId.let {
            ServiceGenerator.getRequestApi()
                .getCommentsOnDetails(it)
                .map(object : io.reactivex.functions.Function<StoryDetailsCommentResult?, Comment> {
                    override fun apply(t: StoryDetailsCommentResult): Comment {
                        val delay = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        return getStoryDetails.transformRawResponseIntoComment(t)
                    }
                })
                .subscribeOn(Schedulers.io())
        }
    }

    fun isFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("StoryDetailsViewModel", "isFavorite: " + dao.loadId(storyId))
            _booleanFavoriteDataState.postValue(dao.loadId(storyId) != null)
        }
    }

    fun favoriteAction(title : String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!_booleanFavoriteDataState.value!!) {
                dao.insert(Favorite(storyId,title))
                _booleanFavoriteDataState.postValue(true)
            } else {
                dao.deleteByStoryId(storyId)
                _booleanFavoriteDataState.postValue(false)
            }
        }
    }

}