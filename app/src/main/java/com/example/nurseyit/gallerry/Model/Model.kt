package com.example.nurseyit.gallerry.Model

interface Model {
    fun getAlbomFromServer(callBack : Model.LoadAlbomCallBack)
    fun getPhotoFromServer(id :Int, callBack: Model.LoadPhotoCallBack) : List<PhotoModel>?

    interface LoadAlbomCallBack{
        fun onAlbomsLoaded(alboms: ArrayList<AlbomModel>)
        fun onDataNotAvailable()
    }

    interface LoadPhotoCallBack{
        fun onPhotoLoaded(imagesList: ArrayList<PhotoModel>)
        fun onPhotoNotAvailable()
    }
}