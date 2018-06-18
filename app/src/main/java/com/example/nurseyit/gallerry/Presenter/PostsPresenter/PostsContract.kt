package com.example.nurseyit.gallerry.Presenter.PostsPresenter

import com.example.nurseyit.gallerry.Model.PostsModel.CommentModel
import com.example.nurseyit.gallerry.Model.PostsModel.PostModel

interface PostsContract {
    interface View {
        fun showPost(listAlbom: List<PostModel>)
        fun showComment(photoList: List<CommentModel>)
        fun showProgressBar()
        fun closeProgressBar()
    }

    interface Presenter {
        fun loadPosts()
        fun loadComments(id: Int)
    }
}