package com.example.nurseyit.gallerry.MainAvtivity.Posts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.nurseyit.gallerry.R
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {
    companion object {
      var  commentsAdapter = CommentsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        val viewManager = LinearLayoutManager(this)

        recyclerviewComments.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = commentsAdapter

        }
    }
}
