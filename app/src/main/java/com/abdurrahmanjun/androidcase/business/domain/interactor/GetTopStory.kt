package com.abdurrahmanjun.androidcase.business.domain.interactor

import android.util.Log
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.domain.models.Story
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class GetTopStory {

    fun getTopStoryObservable(): Observable<java.util.ArrayList<Int>>? {
        return ServiceGenerator.getRequestApi()
            .getStory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun transformArrayIntegerIntoPost(list: List<Int>) : ArrayList<Story> {

        val listOfStory : ArrayList<Story> = arrayListOf<Story>()
        for (i in 0 until list.size) {
            listOfStory.add(
                Story("", true,0, arrayListOf(), list[i], 0, 0,"", "", "")
            )
        }

        return listOfStory
    }

    fun getCommentsObservable(post: Int): Observable<Story> {
        return post.let {
            ServiceGenerator.getRequestApi()
                .getStoryDetails(it)
                .map(object : Function<StoryDetailsResult?, Story> {
                    override fun apply(t: StoryDetailsResult): Story {
                        val delay = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        return transformRawResponseIntoStory(t)
                    }
                })
                .subscribeOn(Schedulers.io())
        }
    }

    fun transformRawResponseIntoStory(response: StoryDetailsResult): Story {
        Log.d("transformRawResponseIntoStory", response.toString())
        return Story(response.by,
            false,
            response.descendants,
            response.kids,
            response.id,
            response.score,
            response.time,
            response.title,
            response.type,
            response.url,
        )
    }
}