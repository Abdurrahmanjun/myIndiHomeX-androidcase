package com.abdurrahmanjun.androidcase.business.datasource.network.story

import retrofit2.http.GET
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsCommentResult
import io.reactivex.Observable
import retrofit2.http.Path

interface HackerNewsStoryApi {
    // https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty
    @GET("topstories.json?print=pretty")
    fun getStory() : Observable<List<Int>>?

    // https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty
    @GET("item/{id}.json?print=pretty")
    fun getStoryDetails(
        @Path("id") id: Int
    ): Observable<StoryDetailsResult>?

    // https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty
    @GET("item/{id}.json?print=pretty")
    fun getCommentsOnDetails(
        @Path("id") id: Int
    ): Observable<StoryDetailsCommentResult>?
}