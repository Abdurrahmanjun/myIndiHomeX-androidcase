package com.abdurrahmanjun.androidcase.presentation.feature.topstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.presentation.feature.storydetails.StoryDetailsFragment

class TopStoryFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View) {
//        if (v.id == R.id.btn_category) {
//            val mStoryDetailsFragment = StoryDetailsFragment()
//            val mFragmentManager = fragmentManager
//            mFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frame_container, mStoryDetailsFragment, StoryDetailsFragment::class.java.simpleName)
//                addToBackStack(null)
//                commit()
//            }
//        }
    }
}