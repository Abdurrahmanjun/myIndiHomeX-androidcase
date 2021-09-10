package com.abdurrahmanjun.androidcase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Comment(
    var by: String,
    var id: Int,
    var kids: List<Int>,
    var parent: Int,
    var text: String,
    var time: Int,
    var type: String,
) : Parcelable