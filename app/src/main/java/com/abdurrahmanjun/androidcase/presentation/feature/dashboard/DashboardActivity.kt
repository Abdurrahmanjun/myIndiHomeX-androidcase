package com.abdurrahmanjun.androidcase.presentation.feature.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.presentation.feature.topstory.TopStoryFragment

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mTopStoryFragment = TopStoryFragment()
        val fragment = mFragmentManager.findFragmentByTag(TopStoryFragment::class.java.simpleName)

        if (fragment !is TopStoryFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mTopStoryFragment, TopStoryFragment::class.java.simpleName)
                .commit()
        }
    }
}