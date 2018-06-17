package com.example.nurseyit.gallerry


import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import com.google.gson.Gson
import org.json.JSONArray
import java.net.URL
import java.util.*

class InternetPhotoWorker : Worker() {

    // Define the parameter keys:
    private val KEY_X_ARG = "X"
    private val KEY_Y_ARG = "Y"
    private val KEY_Z_ARG = "Z"

    // The result key:
    private val KEY_RESULT = "result"

    /**
     * This will be called whenever work manager run the work.
     */
    override fun doWork(): WorkerResult {
        var photos = mutableMapOf<String, Any>()
        val res =  URL("http://jsonplaceholder.typicode.com/photos").readText()
        val arrAlboms = JSONArray(res)
        for (i in 0 until 15) { // Walk through the Array.
            var resAlbom = Gson().fromJson(arrAlboms.getString(i), AlbomModel::class.java)
            var key =  i.toString()
            photos[key] = arrAlboms.getString(i)
            Log.d("Worker", "resPhoto = " + resAlbom.title)
        }


        val output = Data.Builder()
                .putAll(photos)
                .build()

        outputData = output
        // Indicate success or failure with your return value.
        return WorkerResult.SUCCESS
    }
}