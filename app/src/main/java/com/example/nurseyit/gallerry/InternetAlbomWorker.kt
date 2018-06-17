package com.example.nurseyit.gallerry

import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import com.google.gson.Gson
import org.json.JSONArray
import java.net.URL
import java.util.*

class InternetAlbomWorker : Worker() {

    override fun doWork(): WorkerResult {
        var albomsMap = mutableMapOf<String, Any>()
        val response = URL("http://jsonplaceholder.typicode.com/albums").readText()
        val jsonArrayAlboms = JSONArray(response)
        for (i in 0 until 15) {
            var key = i.toString()
            albomsMap[key] = jsonArrayAlboms.getString(i)
        }


        val output = Data.Builder()
                .putAll(albomsMap)
                .build()

        outputData = output

        return WorkerResult.SUCCESS
    }
}