package com.example.nurseyit.gallerry

import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import com.google.gson.Gson
import org.json.JSONArray
import java.lang.Math.random
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class BackgroundInternetAlbomWorker : Worker() {

    override fun doWork(): WorkerResult {
        var albomsMap = mutableMapOf<String, Any>()
        val response = URL("http://jsonplaceholder.typicode.com/albums").readText()
        val jsonArrayAlboms = JSONArray(response)
        var randomList = (1..jsonArrayAlboms.length()).random()
        for (i in 0 until 15) {
            var key = i.toString()
            albomsMap[key] = jsonArrayAlboms.getString(randomList.get(i))
        }


        val output = Data.Builder()
                .putAll(albomsMap)
                .build()

        outputData = output

        return WorkerResult.SUCCESS
    }
}

fun ClosedRange<Int>.random() : ArrayList<Int>{
    var list = ArrayList<Int>()
    for (i in 0 until 15) {
       list.add( Random().nextInt(endInclusive - start) + start)
    }
    return list
}
