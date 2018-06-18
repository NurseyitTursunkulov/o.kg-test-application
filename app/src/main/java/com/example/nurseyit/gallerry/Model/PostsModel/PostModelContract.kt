package com.example.nurseyit.gallerry.Model.PostsModel

import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModel
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel

interface PostModelContract {
    fun getPostFromServer(callBack : LoadPostCallBack)
    fun getCommentFromServer(id :Int, callBack: LoadCommentsCallBack) : List<CommentModel>?

    interface LoadPostCallBack{
        fun onPostLoaded(alboms: ArrayList<PostModel>)
        fun onDataNotAvailable()
    }

    interface LoadCommentsCallBack{
        fun onCommentsLoaded(imagesList: ArrayList<CommentModel>)
        fun onPhotoNotAvailable()
    }
}