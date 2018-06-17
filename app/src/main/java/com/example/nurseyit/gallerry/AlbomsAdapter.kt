package com.example.nurseyit.gallerry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nurseyit.gallerry.Model.AlbomModel
import kotlinx.android.synthetic.main.albomitem.view.*


class AlbomsAdapter(var myDataset: ArrayList<AlbomModel>, val onclick: OnItemClickInterface) :
        RecyclerView.Adapter<AlbomsAdapter.ViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AlbomsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.albomitem, parent, false)

        return ViewHolder(view, onclick,myDataset)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = "id = " + myDataset[position].id.toString()
        holder.userIdTextView.text = "userId = " + myDataset[position].userId.toString()
        holder.titleTextView.text = myDataset[position].title

    }

    override fun getItemCount() = myDataset.size

    interface OnItemClickInterface {
        fun onClick(layoutPosition: Int)

    }
}