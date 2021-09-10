package com.abdurrahmanjun.androidcase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var by: String = "",
    var descendants: Int = 0,
    var id: Int = 0,
    var score: Int = 0,
    var time: Int = 0,
    var title: String = "",
    var type: String = "",
    var url: String = "",
) : Parcelable
