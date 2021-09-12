package com.abdurrahmanjun.androidcase.business.datasource.network;


import com.abdurrahmanjun.androidcase.business.datasource.network.story.HackerNewsStoryApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static HackerNewsStoryApi hackerNewsStoryApi = retrofit.create(HackerNewsStoryApi.class);

    public static HackerNewsStoryApi getRequestApi(){
        return hackerNewsStoryApi;
    }
}
