package com.example.nurseyit.gallerry


import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import com.google.gson.Gson
import org.json.JSONArray
import java.net.URL
import java.util.*

class InternetPhotoWorker : Worker() {

    override fun doWork(): WorkerResult {
        var photos = mutableMapOf<String, Any>()
        val res =  URL("http://jsonplaceholder.typicode.com/photos").readText()
        val arrAlboms = JSONArray(res)
        for (i in 0 until 15) {
            var key =  i.toString()
            photos[key] = arrAlboms.getString(i)
        }

        val output = Data.Builder()
                .putAll(photos)
                .build()

        outputData = output
        return WorkerResult.SUCCESS
    }
}