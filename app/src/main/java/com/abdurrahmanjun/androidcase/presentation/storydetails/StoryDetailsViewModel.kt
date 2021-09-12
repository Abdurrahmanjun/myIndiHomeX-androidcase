package com.abdurrahmanjun.androidcase.presentation.storydetails

import androidx.lifecycle.ViewModel
import com.abdurrahmanjun.androidcase.business.domain.interactor.GetStoryDetails

class StoryDetailsViewModel : ViewModel() {

    // TODO: 10/09/21
    // - get item from activity viewmodel
    // - get data comment from endpoint using retrofit

    val getStoryDetails: GetStoryDetails = GetStoryDetails()
}