package com.example.nurseyit.gallerry

import com.example.nurseyit.gallerry.Model.AlbomModel
import com.example.nurseyit.gallerry.Model.PhotoModel

interface AlbomContract {
    interface View {
        fun showAlbom(listAlbom: List<AlbomModel>)
        fun showGallary(photoList: List<PhotoModel>)
        fun showProgressBar()
    }

    interface Presenter {
        fun loadAlbom()
        fun loadImagesToGallary(id: Int)
    }
}