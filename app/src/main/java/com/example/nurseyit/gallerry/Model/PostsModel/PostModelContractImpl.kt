package com.example.nurseyit.gallerry.Model.PostsModel

import android.arch.lifecycle.Observer
import android.util.Log
import androidx.work.*
import com.example.nurseyit.gallerry.MainAvtivity.Alboms.ownerInstance
import com.example.nurseyit.gallerry.Model.AlbomModel.*
import com.google.gson.Gson


class PostModelContractImpl : PostModelContract {
    override fun getPostFromServer(callBack: PostModelContract.LoadPostCallBack) {
        var myConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val request = OneTimeWorkRequest.Builder(BackgroundInternetPostWorker::class.java)
                .setConstraints(myConstraints)
                .build()

        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(ownerInstance.lifecycleOwner, Observer {
                    it?.let {
                        var postsList: ArrayList<PostModel> = ArrayList()
                        if (it.state.isFinished) {
                            val workerResult = it.outputData

                            workerResult.keyValueMap.forEach {
                                var resAlbom = Gson().fromJson(it.value.toString(), PostModel::class.java)
                                postsList.add(resAlbom)
                            }
                            callBack.onPostLoaded(postsList)


                        } else {
                            callBack.onDataNotAvailable()
                            Log.d("Main", "state not finished albom rep")
                        }
                    }
                })
    }

    override fun getCommentFromServer(id: Int, callBack: PostModelContract.LoadCommentsCallBack): List<CommentModel>? {
        var netWorkConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val input = Data.Builder()
                .putInt("postId", id)
                .build()
        val request = OneTimeWorkRequest.Builder(BackgroundInternetCommentsWorker::class.java)
                .setConstraints(netWorkConstraints)
                .setInputData(input)
                .build()
        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(ownerInstance.lifecycleOwner, Observer {
                    var commentsList: ArrayList<CommentModel> = ArrayList()
                    it?.let {
                        if (it.state.isFinished) {
                            val workerResult = it.outputData
                            commentsList.clear()
                            workerResult.keyValueMap.forEach {
                                var commentModel = Gson().fromJson(it.value.toString(), CommentModel::class.java)
                                commentsList.add(commentModel)
                            }
                            callBack.onCommentsLoaded(commentsList)

                        } else {
                            callBack.onPhotoNotAvailable()
                            Log.d("Main", "state not finished Photo rep")
                        }
                    }
                })
        return null
    }

}