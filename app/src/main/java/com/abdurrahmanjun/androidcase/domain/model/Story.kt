package com.abdurrahmanjun.androidcase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var by: String,
    var descendants: Int,
    var id: Int,
    var kids: List<Int>,
    var score: Int,
    var time: Int,
    var title: String,
    var type: String,
    var url: String,
) : Parcelable
