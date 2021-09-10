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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

        var id: TextView? = null
        var date: TextView? = null
        var title: TextView? = null
        var url: TextView? = null
        var numComments:TextView? = null
        var progressBar: ProgressBar? = null

        init {
            id = itemView.findViewById(R.id.tv_id)
            date = itemView.findViewById(R.id.tv_date)
            title = itemView.findViewById(R.id.tv_titleticket)
            url = itemView.findViewById(R.id.tv_url)
            numComments = itemView.findViewById(R.id.tv_comment)
            progressBar = itemView.findViewById(R.id.progress_bar)
        }

        fun bind(story: Story) {

            if (story.descendantsisload == true) {
                showProgressBar(true)
                date!!.text = ""
                title!!.text = ""
                url!!.text = ""
                numComments!!.text = ""
            } else {
                showProgressBar(false)
                id!!.text = "ID : " + story.id
                date!!.text = getDate(story.time.toLong(), "dd/MM/yyyy hh:mm:ss.SSS")
                title!!.text = story.title
                url!!.text = story.url
                numComments?.text = (String.valueOf(story.descendants)) + " Comments"
            }
        }

        private fun showProgressBar(showProgressBar: Boolean) {
            if (showProgressBar) {
                progressBar!!.visibility = View.VISIBLE
            } else {
                progressBar!!.visibility = View.GONE
            }
        }

        fun getDate(milliSeconds: Long, dateFormat: kotlin.String?): kotlin.String? {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(milliSeconds)
            return formatter.format(calendar.getTime())
        }

    }

}