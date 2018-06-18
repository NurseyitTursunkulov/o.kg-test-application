package com.example.nurseyit.gallerry.Model.PostsModel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nurseyit.gallerry.R
import kotlinx.android.synthetic.main.postitem.view.*

class PostsAdapter(val onclick: OnItemClickInterface) :
        RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    var myDataset: ArrayList<PostModel> = ArrayList()
    class ViewHolder(val view: View, onCustomclick: OnItemClickInterface, myDataset: ArrayList<PostModel>) :
            RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {

                onCustomclick.onClick(myDataset[layoutPosition].id)
            }
        }

        val idTextView = view.idTextView
        val userIdTextView = view.postIdTextView
        val titleTextView = view.nameTextView
        val bodyTextView = view.bodyTextView

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.postitem, parent, false)

        return ViewHolder(view, onclick, myDataset)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = "id = " + myDataset[position].id.toString()
        holder.userIdTextView.text = "userId = " + myDataset[position].userId.toString()
        holder.titleTextView.text = myDataset[position].title
        holder.bodyTextView.text = myDataset[position].body

    }

    override fun getItemCount() = myDataset.size

    interface OnItemClickInterface {
        fun onClick(layoutPosition: Int)

    }
}