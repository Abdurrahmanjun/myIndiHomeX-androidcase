package com.abdurrahmanjun.androidcase.business.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var by: String = "",
    var descendantsisload: Boolean = false,
    var descendants: Int = 0,
    var kids: List<Int>?,
    var id: Int = 0,
    var score: Int = 0,
    var time: Int = 0,
    var title: String = "",
    var type: String = "",
    var url: String? = "",
) : Parcelable
