package com.abdurrahmanjun.androidcase.business.datasource.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.abdurrahmanjun.androidcase.business.datasource.network.story.HackerNewsStoryApi

object ServiceGenerator {
    const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = retrofitBuilder.build()
    val requestApi = retrofit.create(
        HackerNewsStoryApi::class.java
    )
}