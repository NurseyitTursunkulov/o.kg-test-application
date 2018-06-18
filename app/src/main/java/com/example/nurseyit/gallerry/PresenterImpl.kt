package com.example.nurseyit.gallerry

import com.example.nurseyit.gallerry.Model.AlbomModel
import com.example.nurseyit.gallerry.Model.Model
import com.example.nurseyit.gallerry.Model.PhotoModel

class PresenterImpl(val view : AlbomContract.View,val model : Model) : AlbomContract.Presenter {
    override fun loadImagesToGallary(id :Int) {
        model.getPhotoFromServer(id,object  : Model.LoadPhotoCallBack{
            override fun onPhotoLoaded(imagesList: ArrayList<PhotoModel>) {
                view.showGallary(imagesList)
            }

            override fun onPhotoNotAvailable() {
                view.showProgressBar()
            }

        })
    }

    override fun loadAlbom() {
         model.getAlbomFromServer(object :Model.LoadAlbomCallBack{
            override fun onAlbomsLoaded(alboms: ArrayList<AlbomModel>) {
                view.showAlbom(alboms)
            }

            override fun onDataNotAvailable() {
                view.showProgressBar()
            }

        })

    }

}