package com.abdurrahmanjun.androidcase.domain.interactor

import android.util.Log
import com.abdurrahmanjun.androidcase.data.story.repository.source.network.result.StoryDetailsCommentResult
import com.abdurrahmanjun.androidcase.data.story.repository.source.network.result.StoryDetailsResult
import com.abdurrahmanjun.androidcase.domain.model.Comment
import com.abdurrahmanjun.androidcase.domain.model.DetailsStory
import com.abdurrahmanjun.androidcase.domain.model.Story

class GetStoryDetails {
    // execute - details story
    // execute - details comments list
    // execute - post details story

    fun transformArrayIntegerIntoComment(list: List<Int>) : ArrayList<Comment> {

        val listOfStory : ArrayList<Comment> = arrayListOf<Comment>()
        for (i in 0 until list.size) {
            listOfStory.add(
                Comment("", list[i],true, arrayListOf(), "0", 0, "")
            )
        }

        return listOfStory
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