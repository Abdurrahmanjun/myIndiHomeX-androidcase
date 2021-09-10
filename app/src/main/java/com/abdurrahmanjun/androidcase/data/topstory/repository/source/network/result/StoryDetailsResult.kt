package com.abdurrahmanjun.androidcase.data.topstory.repository.source.network.result

data class StoryDetailsResult(
    var by: String,
    var descendants: Int,
    var id: Int,
    var kids: List<Int>,
    var score: Int,
    var time: Int,
    var title: String,
    var type: String,
    var url: String,
)
