package com.example.nurseyit.gallerry.MainAvtivity.Posts

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.example.nurseyit.gallerry.MainAvtivity.Alboms.ownerInstance
import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModel
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel
import com.example.nurseyit.gallerry.Model.PostsModel.CommentModel
import com.example.nurseyit.gallerry.Model.PostsModel.PostModel
import com.example.nurseyit.gallerry.Model.PostsModel.PostModelContractImpl
import com.example.nurseyit.gallerry.Model.PostsModel.PostsAdapter
import com.example.nurseyit.gallerry.Presenter.PostsPresenter.PostContractImpl
import com.example.nurseyit.gallerry.Presenter.PostsPresenter.PostsContract
import com.example.nurseyit.gallerry.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_blank.*


class PostsFragment : Fragment(),
        PostsAdapter.OnItemClickInterface, PostsContract.View {

    lateinit var presenter: PostsContract.Presenter


    lateinit var viewAdapter: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerInstance.lifecycleOwner = this
        presenter = PostContractImpl(this, PostModelContractImpl())


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycleView()
        presenter.loadPosts()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                PostsFragment()
    }

    private fun initRecycleView() {
        val viewManager = LinearLayoutManager(activity)
        viewAdapter = PostsAdapter(this)
        recyclerview3.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }


    override fun onClick(albomId: Int) {
        presenter.loadComments(albomId)
    }



    override fun showPost(postsList: List<PostModel>) {
        viewAdapter.myDataset = postsList as ArrayList<PostModel>
        viewAdapter.notifyDataSetChanged()
    }

    override fun showComment(photoList: List<CommentModel>) {
        val intent = Intent(activity, CommentsActivity::class.java)
        startActivity(intent)
        CommentsActivity.commentsAdapter.commentsList = photoList as ArrayList<CommentModel>
        CommentsActivity.commentsAdapter.notifyDataSetChanged()

    }

    override fun showProgressBar() {
        progressBar3.visibility = View.VISIBLE
        recyclerview3.visibility = View.GONE
    }

    override fun closeProgressBar() {
        progressBar3.visibility = View.GONE
        recyclerview3.visibility = View.VISIBLE
    }


}

private fun java.util.ArrayList<PhotoModel>.url(): java.util.ArrayList<String>? {
    val url = ArrayList<String>()
    this.forEach {
        url.add(it.url)
    }
    return url
}

