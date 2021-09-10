package com.abdurrahmanjun.androidcase.presentation.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.domain.model.Comment
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    private var comments: ArrayList<Comment> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.CommentHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, null, false)
        return CommentHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(comments.get(position))
    }

    fun notifyCommentsValueChange(value: ArrayList<Comment>) {
        this.comments = value
        notifyDataSetChanged()
    }

    fun updateComment(comments: Comment?) {
        comments?.let { this.comments.set(getIndexOf(comments.id), it) }
        notifyItemChanged(this.comments.indexOf(comments))
    }

    fun getIndexOf(id: Int): Int {
        var pos = 0
        for (myObj in this.comments) {
            if (id.equals(myObj.id)) return pos
            pos++
        }
        return -1
    }

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var date: TextView? = null
        var commentText: TextView? = null
        var numReply: TextView? = null
        var progressBar: ProgressBar? = null

        init {
            name = itemView.findViewById(R.id.tv_name)
            date = itemView.findViewById(R.id.tv_date)
            commentText = itemView.findViewById(R.id.tv_comment)
            numReply = itemView.findViewById(R.id.tv_comment_replied)
            progressBar = itemView.findViewById(R.id.progress_bar)
        }

        fun bind(comment: Comment) {
            name!!.text = comment.by
            if (comment.commentisload == true) {
                showProgressBar(true)
                date!!.text = ""
                commentText!!.text = ""
                numReply!!.text = ""
            } else {
                showProgressBar(false)
                date!!.text = getDate(comment.time.toLong(), "dd/MM/yyyy hh:mm:ss.SSS")
                commentText!!.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(comment.text, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(comment.text)
                }

                numReply?.text = (comment.kids?.let { String.valueOf(it.size) }) + " Comments"
            }

            // on clicklistener to continue explore showing comment reply
            itemView.setOnClickListener {}
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