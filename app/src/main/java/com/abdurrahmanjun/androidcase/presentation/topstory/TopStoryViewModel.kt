package com.abdurrahmanjun.androidcase.presentation.topstory

import androidx.lifecycle.ViewModel
import com.abdurrahmanjun.androidcase.business.domain.interactor.GetTopStory

class TopStoryViewModel : ViewModel() {

    // TODO: 10/09/21
    // - get data from room
    // - get data from endpoint using retrofit

    val getTopStory: GetTopStory = GetTopStory()
    
}