package com.abdurrahmanjun.androidcase.presentation.storydetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.business.domain.models.Comment
import com.abdurrahmanjun.androidcase.presentation.utils.ViewUtils.Companion.getDate
import java.util.*

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    private var comments: ArrayList<Comment> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
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
                progressBar?.visibility = View.VISIBLE
                date!!.text = ""
                commentText!!.text = ""
                numReply!!.text = ""
            } else {
                progressBar?.visibility = View.GONE
                date!!.text = getDate(comment.time.toLong(), "dd/MM/yyyy hh:mm:ss.SSS")
                commentText!!.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(comment.text, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(comment.text)
                }

                numReply?.text = "0 Comments"
                comment.kids?.let {
                    if (it.size > 0) {
                        numReply?.text = "$it.size Comments"
                    }
                }
            }

            // on clicklistener to continue explore showing comment reply
            itemView.setOnClickListener {}
        }
    }

}