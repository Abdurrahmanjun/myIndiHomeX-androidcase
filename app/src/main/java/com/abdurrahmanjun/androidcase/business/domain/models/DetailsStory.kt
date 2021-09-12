package com.abdurrahmanjun.androidcase.business.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsStory(
    var story: Story,
    var listOfComments: List<Comment>
) : Parcelable