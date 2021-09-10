package com.abdurrahmanjun.androidcase.domain.interactor

import com.abdurrahmanjun.androidcase.data.ServiceGenerator
import com.abdurrahmanjun.androidcase.data.topstory.repository.source.network.result.StoryDetailsResult
import com.abdurrahmanjun.androidcase.domain.model.Story
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class GetTopStory {

    fun execute(): Observable<ArrayList<Story>>? {
        return ServiceGenerator.getRequestApi()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                transformArrayIntegerIntoPost(list)
            }
    }

    fun transformArrayIntegerIntoPost(list: ArrayList<Int>) : ArrayList<Story> {

        val listOfStory : ArrayList<Story> = arrayListOf<Story>()
        for (i in 0 until list.size) {
            listOfStory.add(
                Story("", 0, list[i], 0, 0,"", "", "")
            )
        }

        return listOfStory
    }

    fun execute(story: Story): Observable<Story>? {
        return ServiceGenerator.getRequestApi()
            .getComments(story.id)
            .subscribeOn(Schedulers.io())
            .map { response ->
                transformRawResponseIntoStory(response)
            }
    }

    private fun transformRawResponseIntoStory(response: StoryDetailsResult): Story? {
        return Story(response.by,
            response.descendants,
            response.id,
            response.score,
            response.time,
            response.title,
            response.type,
            response.url
        )
    }
}