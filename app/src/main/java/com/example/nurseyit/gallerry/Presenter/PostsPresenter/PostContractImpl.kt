package com.example.nurseyit.gallerry.Presenter.PostsPresenter

import com.example.nurseyit.gallerry.Model.PostsModel.CommentModel
import com.example.nurseyit.gallerry.Model.PostsModel.PostModel
import com.example.nurseyit.gallerry.Model.PostsModel.PostModelContract


class PostContractImpl(val view : PostsContract.View, val model : PostModelContract) : PostsContract.Presenter {
    override fun loadPosts() {
        model.getPostFromServer(object : PostModelContract.LoadPostCallBack{
            override fun onPostLoaded(posts: ArrayList<PostModel>) {
                view.closeProgressBar()
                view.showPost(posts)
            }

            override fun onDataNotAvailable() {
                view.showProgressBar()
            }

        })
    }

    override fun loadComments(id: Int) {
        model.getCommentFromServer(id, object : PostModelContract.LoadCommentsCallBack{
            override fun onCommentsLoaded(commentsList: ArrayList<CommentModel>) {
                view.closeProgressBar()
                view.showComment(commentsList)
            }

            override fun onPhotoNotAvailable() {
                view.showProgressBar()
            }

        })
    }

}