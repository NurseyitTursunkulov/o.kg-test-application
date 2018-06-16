package com.example.nurseyit.gallerry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.albomitem.view.*


class AlbomsAdapter(var myDataset: ArrayList<AlbomModel>, val onclick: OnItemClickInterface) :
        RecyclerView.Adapter<AlbomsAdapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View, onCustomclick: OnItemClickInterface,myDataset: ArrayList<AlbomModel>) :
            RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {

                onCustomclick.onClick(myDataset[layoutPosition].id)
            }
        }

        val idTextView = view.idTextView
        val userIdTextView = view.userIdTextView
        val titleTextView = view.title


    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AlbomsAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.albomitem, parent, false)

        // set the view's size, margins, paddings and layout parameters
        view.setOnClickListener {  }
        return ViewHolder(view, onclick,myDataset)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.idTextView.text = "id = " + myDataset[position].id.toString()
        holder.userIdTextView.text = "userId = " + myDataset[position].userId.toString()
        holder.titleTextView.text = myDataset[position].title

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    interface OnItemClickInterface {
        fun onClick(layoutPosition: Int)

    }
}