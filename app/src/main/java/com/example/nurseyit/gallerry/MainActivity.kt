package com.example.nurseyit.gallerry

import android.arch.lifecycle.LifecycleOwner
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
import kotlin.collections.ArrayList
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.work.*
import com.example.nurseyit.gallerry.Model.AlbomModel
import com.example.nurseyit.gallerry.Model.PhotoModel
import com.example.nurseyit.gallerry.Model.getAsyncAlboms
import com.example.nurseyit.gallerry.Model.initGallaryLib
import com.example.nurseyit.gallerry.ownerInstance.lifecycleOwner

class MainActivity : AppCompatActivity(), ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader, AlbomsAdapter.OnItemClickInterface {


    var alboms: ArrayList<AlbomModel> = ArrayList()
    var myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    lateinit var viewAdapter: AlbomsAdapter
    lateinit var viewManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleOwner = this
        initGallaryLib(this,this)
        initRecycleView()
        getAsyncAlboms(myConstraints,alboms,viewAdapter,this,this)

    }



    private fun initRecycleView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = AlbomsAdapter(alboms, this)
        recyclerview2.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    private fun displayImagesInGallary(id: Int, images: ArrayList<PhotoModel>) {
        val intent = Intent(this@MainActivity, ImageGalleryActivity::class.java)
        Log.d("Main", " images =  " + images.toString())
        val bundle = Bundle()
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, images.url())
        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images")
        intent.putExtras(bundle)

        startActivity(intent)
    }

    override fun onClick(albomId: Int) {
        displayAlbomRelatedImagesInGallery(albomId)
    }

    private fun displayAlbomRelatedImagesInGallery(albomId: Int){
        progressBar.visibility = View.VISIBLE
        recyclerview2.visibility = View.GONE
        val request = OneTimeWorkRequest.Builder(InternetPhotoWorker::class.java)
                .setConstraints(myConstraints)
                .build()
        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance()
                .getStatusById(request.id)
                .observe(this@MainActivity, Observer {
                    var imagesList: ArrayList<PhotoModel> = ArrayList()
                    it?.let {
                        if (it.state.isFinished) {
                            val workerResult = it.outputData
                            imagesList.clear()
                            workerResult.keyValueMap.forEach {
                                var photoModel = Gson().fromJson(it.value.toString(), PhotoModel::class.java)
                                imagesList.add(photoModel)
                            }

                            progressBar.visibility = View.GONE
                            recyclerview2.visibility = View.VISIBLE
                            displayImagesInGallary(albomId, imagesList)

                            Toast.makeText(this, "Work completed. imagesList", Toast.LENGTH_LONG)
                                    .show()
                        } else {
                            Toast.makeText(this, "Work failed. imagesList", Toast.LENGTH_LONG)
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

private fun java.util.ArrayList<PhotoModel>.url(): java.util.ArrayList<String>? {
    val url = ArrayList<String>()
    this.forEach {
        url.add(it.url)
    }
    return url
}
