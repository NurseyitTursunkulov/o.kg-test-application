package com.example.nurseyit.gallerry.Presenter.AlbomPresenter

import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModel
import com.example.nurseyit.gallerry.Model.AlbomModel.Model
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel

class PresenterImpl(val view : AlbomContract.View, val model : Model) : AlbomContract.Presenter {
    override fun loadImagesToGallary(id :Int) {
        model.getPhotoFromServer(id,object  : Model.LoadPhotoCallBack{
            override fun onPhotoLoaded(imagesList: ArrayList<PhotoModel>) {
                view.closeProgressBar()
                view.showGallary(imagesList)
            }

            override fun onPhotoNotAvailable() {
                view.showProgressBar()
            }

        })
    }

    override fun loadAlbom() {
         model.getAlbomFromServer(object : Model.LoadAlbomCallBack{
            override fun onAlbomsLoaded(alboms: ArrayList<AlbomModel>) {
                view.closeProgressBar()
                view.showAlbom(alboms)
            }

            override fun onDataNotAvailable() {
                view.showProgressBar()
            }

        })

    }

}