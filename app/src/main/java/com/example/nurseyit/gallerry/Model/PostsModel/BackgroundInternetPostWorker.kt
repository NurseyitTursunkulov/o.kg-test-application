package com.example.nurseyit.gallerry.Model.PostsModel

import androidx.work.Data
import androidx.work.Worker
import org.json.JSONArray
import java.net.URL
import java.util.*

class BackgroundInternetPostWorker : Worker() {

    override fun doWork(): WorkerResult {
        var postesMap = mutableMapOf<String, Any>()
        val response = URL("http://jsonplaceholder.typicode.com/posts").readText()
        val jsonArrayPosts = JSONArray(response)
        var randomList = (1..jsonArrayPosts.length()).random()
        for (i in 0 until 15) {
            var key = i.toString()
            postesMap[key] = jsonArrayPosts.getString(randomList.get(i))
        }


        val output = Data.Builder()
                .putAll(postesMap)
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