package com.example.nurseyit.gallerry.Model.PostsModel

import androidx.work.Data
import androidx.work.Worker
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel
import com.google.gson.Gson
import org.json.JSONArray
import java.net.URL


class BackgroundInternetCommentsWorker : Worker() {

    override fun doWork(): WorkerResult {
        var comments = mutableMapOf<String, Any>()
        var id = inputData.getInt("postId",0)
        val res =  URL("http://jsonplaceholder.typicode.com/comments").readText()
        val arrAlboms = JSONArray(res)
        for (i in 0 until arrAlboms.length()) {
            var commentsModel = Gson().fromJson(arrAlboms.getString(i), CommentModel::class.java)
            if (commentsModel.postId == id){
                var key =  i.toString()
                comments[key] = arrAlboms.getString(i)
                if (comments.size >= 15) break
            }

        }

        val output = Data.Builder()
                .putAll(comments)
                .build()

        outputData = output
        return WorkerResult.SUCCESS
    }
}