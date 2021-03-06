package com.example.nurseyit.gallerry.Model.AlbomModel


import androidx.work.Data
import androidx.work.Worker
import com.google.gson.Gson
import org.json.JSONArray
import java.net.URL

class BackgroundInternetPhotoWorker : Worker() {

    override fun doWork(): WorkerResult {
        var photos = mutableMapOf<String, Any>()
       var id = inputData.getInt("photoId",0)
        val res =  URL("http://jsonplaceholder.typicode.com/photos").readText()
        val arrAlboms = JSONArray(res)
        for (i in 0 until arrAlboms.length()) {
            var photoModel = Gson().fromJson(arrAlboms.getString(i), PhotoModel::class.java)
            if (photoModel.albumId == id){
                var key =  i.toString()
                photos[key] = arrAlboms.getString(i)
                if (photos.size >= 15) break
            }

        }

        val output = Data.Builder()
                .putAll(photos)
                .build()

        outputData = output
        return WorkerResult.SUCCESS
    }
}