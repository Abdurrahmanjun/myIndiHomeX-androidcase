package com.abdurrahmanjun.androidcase.data.topstory.repository.source.network.result

import com.google.gson.annotations.SerializedName

data class StoryDetailsResult(
    @SerializedName("by") var by: String,
    @SerializedName("descendants") var descendants: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("kids") var kids: List<Int>,
    @SerializedName("score") var score: Int,
    @SerializedName("time") var time: Int,
    @SerializedName("title") var title: String,
    @SerializedName("type") var type: String,
    @SerializedName("url") var url: String,
)
