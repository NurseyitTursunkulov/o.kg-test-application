package com.example.nurseyit.gallerry.Presenter.AlbomPresenter

import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModel
import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModelContract
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel

class AlbomContractImpl(val view : AlbomContract.View, val model : AlbomModelContract) : AlbomContract.Presenter {
    override fun loadImagesToGallary(id :Int) {
        model.getPhotoFromServer(id,object  : AlbomModelContract.LoadPhotoCallBack{
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
         model.getAlbomFromServer(object : AlbomModelContract.LoadAlbomCallBack{
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