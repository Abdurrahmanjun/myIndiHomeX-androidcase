package com.abdurrahmanjun.androidcase.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdurrahmanjun.androidcase.domain.model.Story

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    private lateinit var stories: ArrayList<Story>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    fun notifyStoriesValueChange(Stories: ArrayList<Story>) {
        this.stories = Stories
        notifyDataSetChanged()
    }

    fun updateStory(story: Story?) {
        story?.let { this.stories.set(this.stories.indexOf(story), it) }
        notifyItemChanged(this.stories.indexOf(story))
    }

    class StoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}