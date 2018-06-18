package com.example.nurseyit.gallerry.Model.AlbomModel

import android.arch.lifecycle.Observer
import android.util.Log
import androidx.work.*
import com.example.nurseyit.gallerry.MainAvtivity.Alboms.ownerInstance.lifecycleOwner
import com.google.gson.Gson

class RepositoryImpl : AlbomModelContract {
    override fun getAlbomFromServer(callBack : AlbomModelContract.LoadAlbomCallBack) {
        var myConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val request = OneTimeWorkRequest.Builder(BackgroundInternetAlbomWorker::class.java)
                .setConstraints(myConstraints)
                .build()

        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(lifecycleOwner, Observer {
                    it?.let {
                        var alboms: ArrayList<AlbomModel> = ArrayList()
                        if (it.state.isFinished) {
                            val workerResult = it.outputData

                            workerResult.keyValueMap.forEach {
                                var resAlbom = Gson().fromJson(it.value.toString(), AlbomModel::class.java)
                                alboms.add(resAlbom)
                            }
                            callBack.onAlbomsLoaded(alboms)


                        } else {
                            callBack.onDataNotAvailable()
                            Log.d("Main", "state not finished albom rep")
                        }
                    }
                })

    }

    override fun getPhotoFromServer(id: Int, callBack: AlbomModelContract.LoadPhotoCallBack): List<PhotoModel>? {
        var netWorkConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val input = Data.Builder()
                .putInt("photoId", id)
                .build()
        val request = OneTimeWorkRequest.Builder(BackgroundInternetPhotoWorker::class.java)
                .setConstraints(netWorkConstraints)
                .setInputData(input)
                .build()
        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(lifecycleOwner, Observer {
                    var imagesList: ArrayList<PhotoModel> = ArrayList()
                    it?.let {
                        if (it.state.isFinished) {
                            val workerResult = it.outputData
                            imagesList.clear()
                            workerResult.keyValueMap.forEach {
                                var photoModel = Gson().fromJson(it.value.toString(), PhotoModel::class.java)
                                imagesList.add(photoModel)
                            }
                            callBack.onPhotoLoaded(imagesList)

                        } else {
                            callBack.onPhotoNotAvailable()
                            Log.d("Main", "state not finished Photo rep")
                        }
                    }
                })
        return null
    }

}