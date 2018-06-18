package com.example.nurseyit.gallerry.Model.AlbomModel

interface AlbomModelContract {
    fun getAlbomFromServer(callBack : LoadAlbomCallBack)
    fun getPhotoFromServer(id :Int, callBack: LoadPhotoCallBack) : List<PhotoModel>?

    interface LoadAlbomCallBack{
        fun onAlbomsLoaded(alboms: ArrayList<AlbomModel>)
        fun onDataNotAvailable()
    }

    interface LoadPhotoCallBack{
        fun onPhotoLoaded(imagesList: ArrayList<PhotoModel>)
        fun onPhotoNotAvailable()
    }
}