package com.abdurrahmanjun.androidcase.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.domain.model.Story
import java.lang.String

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    private var stories: ArrayList<Story> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_story, null, false)
        return StoryHolder(view)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(stories.get(position))
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    fun notifyStoriesValueChange(value: ArrayList<Story>) {
        this.stories = value
//        this.storyIds = storyIds
        notifyDataSetChanged()
    }

    fun updateStory(story: Story?) {
        story?.let { this.stories.set(getIndexOf(story.id), it) }
        notifyItemChanged(this.stories.indexOf(story))
    }

    fun getIndexOf(id: Int): Int {
        var pos = 0
        for (myObj in this.stories) {
            if (id.equals(myObj.id)) return pos
            pos++
        }
        return -1
    }

    class StoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView? = null
        var numComments:TextView? = null

        init {
            title = itemView.findViewById(R.id.title)
            numComments = itemView.findViewById(R.id.tv_comment)
        }

        fun bind(story: Story) {

            title?.setText(story.title)
            if (story.descendantsisload == true) {
//                showProgressBar(true)
                numComments!!.text = ""
            } else {
//                showProgressBar(false)
                numComments?.text = (String.valueOf(story.descendants)) + " Comments"
            }
        }

//        private fun showProgressBar(showProgressBar: Boolean) {
//            if (showProgressBar) {
//                progressBar!!.visibility = View.VISIBLE
//            } else {
//                progressBar!!.visibility = View.GONE
//            }
//        }

    }

}