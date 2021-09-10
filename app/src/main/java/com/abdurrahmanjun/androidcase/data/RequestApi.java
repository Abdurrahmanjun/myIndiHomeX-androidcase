package com.abdurrahmanjun.androidcase.data;

import com.abdurrahmanjun.androidcase.data.topstory.repository.source.network.result.StoryDetailsResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {

    // https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty
    @GET("topstories.json?print=pretty")
    Observable<ArrayList<Integer>> getPosts();

    // https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty
    @GET("item/{id}.json?print=pretty")
    Observable<StoryDetailsResult> getComments(
            @Path("id") int id
    );
}
