package com.abdurrahmanjun.androidcase.domain.interactor

import android.util.Log
import com.abdurrahmanjun.androidcase.data.ServiceGenerator
import com.abdurrahmanjun.androidcase.data.topstory.repository.source.network.result.StoryDetailsResult
import com.abdurrahmanjun.androidcase.domain.model.Story
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class GetTopStory {

    // execute - list of top story
    // execute - comments every item list top story
    // execute - get favorite headline

    fun execute(): Observable<ArrayList<Story>>? {
        return ServiceGenerator.getRequestApi()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                transformArrayIntegerIntoPost(list)
            }
    }

    fun transformArrayIntegerIntoPost(list: List<Int>) : ArrayList<Story> {

        val listOfStory : ArrayList<Story> = arrayListOf<Story>()
        for (i in 0 until list.size) {
            listOfStory.add(
                Story("", true,0, list[i], 0, 0,"", "", "")
            )
        }

        return listOfStory
    }

    fun execute(story: Story): Observable<Story>? {
        return story.id?.let {
            ServiceGenerator.getRequestApi()
                .getComments(it)
                .subscribeOn(Schedulers.io())
                .map { response ->
                    transformRawResponseIntoStory(response)
                }
        }
    }

    fun transformRawResponseIntoStory(response: StoryDetailsResult): Story {
        Log.d("transformRawResponseIntoStory", response.toString())
        return Story(response.by,
            false,
            response.descendants,
            response.id,
            response.score,
            response.time,
            response.title,
            response.type,
            response.url,
        )
    }
}