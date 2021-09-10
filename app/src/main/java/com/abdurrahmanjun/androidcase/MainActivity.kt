package com.abdurrahmanjun.androidcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdurrahmanjun.androidcase.presentation.feature.topstory.TopStoryFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mTopStoryFragment = TopStoryFragment()
        val fragment = mFragmentManager.findFragmentByTag(TopStoryFragment::class.java.simpleName)

        if (fragment !is TopStoryFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + TopStoryFragment::class.java.simpleName)
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mTopStoryFragment, TopStoryFragment::class.java.simpleName)
                .commit()
        }
    }
}