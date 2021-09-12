package com.abdurrahmanjun.androidcase.business.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopStory(
    var listOfFavoriteStories: List<String>,
    var listOfTopStories: List<Story>
) : Parcelable
