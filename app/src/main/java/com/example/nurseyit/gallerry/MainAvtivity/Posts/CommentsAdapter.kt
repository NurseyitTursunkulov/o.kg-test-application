package com.example.nurseyit.gallerry.MainAvtivity.Posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nurseyit.gallerry.Model.PostsModel.CommentModel
import com.example.nurseyit.gallerry.R
import kotlinx.android.synthetic.main.albomitem.view.*
import kotlinx.android.synthetic.main.commentsitem.view.*

class CommentsAdapter  : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){
    var commentsList: ArrayList<CommentModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.commentsitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return  commentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = "id = " + commentsList[position].id.toString()
        holder.postIdTextView.text =  "userId = " + commentsList[position].postId.toString()
        holder.nameTextView.text = commentsList[position].name
        holder.emailTextView.text = commentsList[position].email
        holder.bodyTextView.text = commentsList[position].body
    }

    class ViewHolder(val view: View) :
            RecyclerView.ViewHolder(view) {
        val idTextView = view.commentsIdTextView
        val postIdTextView = view.postIdTextViewInComments
        val nameTextView = view.nameTextViewInComments
        val emailTextView = view.emailTextView
        val bodyTextView = view.bodyTextView


    }
}
