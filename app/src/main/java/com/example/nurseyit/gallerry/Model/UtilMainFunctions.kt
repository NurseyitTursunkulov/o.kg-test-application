package com.example.nurseyit.gallerry.Model

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter
import com.example.nurseyit.gallerry.AlbomsAdapter
import com.example.nurseyit.gallerry.InternetAlbomWorker
import com.google.gson.Gson

fun initGallaryLib(thumbnailLoader : ImageGalleryAdapter.ImageThumbnailLoader, fullScreen : FullScreenImageGalleryAdapter.FullScreenImageLoader) {
    ImageGalleryActivity.setImageThumbnailLoader(thumbnailLoader)
    FullScreenImageGalleryActivity.setFullScreenImageLoader(fullScreen)
}

fun getAsyncAlboms(myConstraints: Constraints, alboms: ArrayList<AlbomModel>,
                   viewAdapter: AlbomsAdapter, context: Context, owner: LifecycleOwner) {
    val request = OneTimeWorkRequest.Builder(InternetAlbomWorker::class.java)
            .setConstraints(myConstraints)
            .build()

    WorkManager.getInstance().enqueue(request)

    WorkManager.getInstance()
            .getStatusById(request.id)
            .observe(owner, Observer {
                it?.let {

                    if (it.state.isFinished) {
                        val workerResult = it.outputData

                        alboms.clear()
                        workerResult.keyValueMap.forEach {
                            var resAlbom = Gson().fromJson(it.value.toString(), AlbomModel::class.java)
                            alboms.add(resAlbom)
                        }
                        viewAdapter.notifyDataSetChanged()
                        Toast.makeText(context, "Work completed albom.", Toast.LENGTH_LONG)
                                .show()
                    } else {
                        Toast.makeText(context, "Work failed.albom", Toast.LENGTH_LONG)
                                .show()
                    }
                }
            })
}

