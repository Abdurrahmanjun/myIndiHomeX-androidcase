package com.abdurrahmanjun.androidcase.presentation.feature.storydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryDetailsBinding
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryListBinding
import com.abdurrahmanjun.androidcase.presentation.adapter.CommentAdapter
import com.abdurrahmanjun.androidcase.presentation.adapter.StoryAdapter

class StoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStoryDetailsBinding
    private lateinit var adapter: CommentAdapter
    private var flagFav : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rlHeart.setOnClickListener { v: View? ->
            favClicked(binding.productLoved,binding.productLovedIdle)
        }
    }
    fun favClicked(product_loved : ImageView, product_loved_no : ImageView) {
        if (!flagFav) {
            animateHeart(product_loved, product_loved_no, true)
            flagFav = true
        } else {
            animateHeart(product_loved, product_loved_no, false)
            flagFav = false
        }
    }

    fun animateHeart(imageLovedOn: ImageView, imageLovedOff: ImageView, on: Boolean) {
        imageLovedOff.animate().scaleX(if (on) 0.toFloat() else 1.toFloat()).scaleY(if (on) 0.toFloat() else 1.toFloat()).alpha(if (on) 0.toFloat() else 1.toFloat())
        imageLovedOn.animate().scaleX(if (on) 1.toFloat() else 0.toFloat()).scaleY(if (on) 1.toFloat() else 0.toFloat()).alpha(if (on) 1.toFloat() else 0.toFloat())
    }
}