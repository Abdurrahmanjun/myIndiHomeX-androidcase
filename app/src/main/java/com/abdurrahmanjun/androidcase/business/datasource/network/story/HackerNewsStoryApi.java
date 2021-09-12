package com.abdurrahmanjun.androidcase.business.datasource.network.story;

import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsCommentResult;
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsStoryApi {

    // https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty
    @GET("topstories.json?print=pretty")
    Observable<ArrayList<Integer>> getStory();

    // https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty
    @GET("item/{id}.json?print=pretty")
    Observable<StoryDetailsResult> getStoryDetails(
            @Path("id") int id
    );

    // https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty
    @GET("item/{id}.json?print=pretty")
    Observable<StoryDetailsCommentResult> getCommentsOnDetails(
            @Path("id") int id
    );
}
