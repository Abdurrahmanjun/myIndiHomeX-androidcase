package com.abdurrahmanjun.androidcase.data.story.repository.source.network.result

import com.google.gson.annotations.SerializedName

data class StoryDetailsCommentResult(
    @SerializedName("by") var by: String,
    @SerializedName("id") var id: Int,
    @SerializedName("kids") var kids: List<Int>,
    @SerializedName("parent") var parent: Int,
    @SerializedName("time") var time: Int,
    @SerializedName("text") var text: String,
    @SerializedName("type") var type: String,
)
