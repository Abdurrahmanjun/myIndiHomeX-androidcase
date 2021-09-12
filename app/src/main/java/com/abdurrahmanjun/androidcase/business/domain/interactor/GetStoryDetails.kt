package com.abdurrahmanjun.androidcase.business.domain.interactor

import android.util.Log
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsCommentResult
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.domain.models.Comment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList
import kotlin.random.Random

class GetStoryDetails {

    fun getDetailsStoryObservable(storyId : Int): Observable<StoryDetailsResult>? {
        return ServiceGenerator.requestApi
            .getStoryDetails(storyId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    fun transformArrayIntegerIntoComment(list: List<Int>) : ArrayList<Comment> {

        val listOfStory : ArrayList<Comment> = arrayListOf<Comment>()
        for (i in 0 until list.size) {
            listOfStory.add(
                Comment("", list[i],true, arrayListOf(), "0", 0, "")
            )
        }

        return listOfStory
    }

    fun getCommentsDetailsObservable(commentId: Int): Observable<Comment>? {
        return commentId.let {
            ServiceGenerator.requestApi
                .getCommentsOnDetails(it)
                ?.map(object : io.reactivex.functions.Function<StoryDetailsCommentResult?, Comment> {
                    override fun apply(t: StoryDetailsCommentResult): Comment {
                        val delay = (Random.nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        return transformRawResponseIntoComment(t)
                    }
                })
                ?.subscribeOn(Schedulers.io())
        }
    }

    fun transformRawResponseIntoComment(response: StoryDetailsCommentResult): Comment {
        Log.d("transformRawResponseIntoComment", response.toString())
        return Comment(
            response.by,
            response.id,
            false,
            response.kids,
            response.text,
            response.time,
            response.type,
        )
    }
}