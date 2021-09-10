package com.abdurrahmanjun.androidcase.presentation.feature.storydetails

import androidx.lifecycle.ViewModel
import com.abdurrahmanjun.androidcase.domain.interactor.GetStoryDetails
import com.abdurrahmanjun.androidcase.domain.interactor.GetTopStory

class StoryDetailsViewModel : ViewModel() {

    // TODO: 10/09/21
    // - get item from activity viewmodel
    // - get data comment from endpoint using retrofit

    val getStoryDetails: GetStoryDetails = GetStoryDetails()
}