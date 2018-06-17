package com.example.nurseyit.gallerry

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL
import org.json.JSONArray
import kotlin.collections.ArrayList
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.work.*

class MainActivity : AppCompatActivity(), ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader, AlbomsAdapter.OnItemClickInterface {



    var alboms: ArrayList<AlbomModel> = ArrayList()
    lateinit var myConstraints: Constraints
    lateinit var viewAdapter : AlbomsAdapter
    lateinit var viewManager : LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageGalleryActivity.setImageThumbnailLoader(this)
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this)
        viewManager = LinearLayoutManager(this)
         viewAdapter = AlbomsAdapter(alboms,this)
        recyclerview2.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        myConstraints  = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val request = OneTimeWorkRequest.Builder(InternetAlbomWorker::class.java)
                .setConstraints(myConstraints)
                .build()

        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(this@MainActivity, Observer {
                    it?.let{
                        // Get the output data from the worker.

                        // Check if the task is finished?
                        if (it.state.isFinished) {
                            val workerResult = it.outputData

                            alboms.clear()
                            workerResult.keyValueMap.forEach {
                                var resAlbom = Gson().fromJson(it.value.toString(), AlbomModel::class.java)
                                alboms.add(resAlbom)
                            }
                            viewAdapter.notifyDataSetChanged()
                            Toast.makeText(this, "Work completed albom.", Toast.LENGTH_LONG)
                                    .show()
                        } else {
                            Toast.makeText(this, "Work failed.albom", Toast.LENGTH_LONG)
                                    .show()
                        }
                    }
                })

    }

    private fun displayImagesInGallary(id : Int, images: ArrayList<PhotoModel> ) {
        val intent = Intent(this@MainActivity, ImageGalleryActivity::class.java)
        Log.d("Main", " images =  " + images.toString())
        val bundle = Bundle()
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, images.url())
        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images")
        intent.putExtras(bundle)

        startActivity(intent)
    }

    override fun onClick(albomId: Int) {

//        displayImagesInGallary(albomId)
        progressBar.visibility = View.VISIBLE
        recyclerview2.visibility = View.GONE
        val request = OneTimeWorkRequest.Builder(InternetPhotoWorker::class.java)
                .setConstraints(myConstraints)
                .build()
        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(this@MainActivity, Observer {
                    var images: ArrayList<PhotoModel> = ArrayList()
                    it?.let {
                        // Get the output data from the worker.

                        // Check if the task is finished?
                        if (it.state.isFinished) {
                            val workerResult = it.outputData

                            images.clear()
                            workerResult.keyValueMap.forEach {
                                var resAlbom = Gson().fromJson(it.value.toString(), PhotoModel::class.java)
                                images.add(resAlbom)
                            }

                            progressBar.visibility = View.GONE
                            recyclerview2.visibility = View.VISIBLE
                            displayImagesInGallary(albomId,images)

                            Toast.makeText(this, "Work completed. images", Toast.LENGTH_LONG)
                                    .show()
                        } else {
                            Toast.makeText(this, "Work failed. images", Toast.LENGTH_LONG)
                                    .show()
                        }
                    }
                })

    }

    override fun loadImageThumbnail(iv: ImageView?, imageUrl: String?, dimension: Int) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv?.context)
                    .load(imageUrl)
                    .resize(dimension, dimension)
                    .centerCrop()
                    .into(iv)
        } else {
            iv?.setImageDrawable(null)
        }
    }

    override fun loadFullScreenImage(iv: ImageView?, imageUrl: String?, width: Int, bglinearLayout: LinearLayout?) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv?.context)
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv, object : Callback {
                        override fun onSuccess() {
                            val bitmap = (iv?.drawable as BitmapDrawable).bitmap
                            Palette.from(bitmap).generate { palette -> applyPalette(palette, bglinearLayout!!) }
                        }

                        override fun onError() {

                        }
                    })
        } else {
            iv?.setImageDrawable(null)
        }

    }

    private fun applyPalette(palette: Palette, viewGroup: ViewGroup) {
        val bgColor = palette.getLightVibrantColor(0x000000);
        if (bgColor != -1)
            viewGroup.setBackgroundColor(bgColor)
    }
}

private fun  java.util.ArrayList<PhotoModel>.url(): java.util.ArrayList<String>? {
    val url = ArrayList<String>()
    this.forEach {
        url.add(it.url)
    }
    return url
}
