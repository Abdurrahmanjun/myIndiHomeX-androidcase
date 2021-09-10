package com.abdurrahmanjun.androidcase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Comment(
    var by: String? = "",
    var id: Int = 0,
    var commentisload: Boolean = false,
    var kids: List<Int>?,
    var text: String? = "",
    var time: Int = 0,
    var type: String = "",
) : Parcelable