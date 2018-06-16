package com.example.nurseyit.gallerry

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
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

class MainActivity : AppCompatActivity(), ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader, AlbomsAdapter.OnItemClickInterface {


    var images: ArrayList<PhotoModel> = ArrayList()
    var alboms: ArrayList<AlbomModel> = ArrayList()
    lateinit var viewAdapter : AlbomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageGalleryActivity.setImageThumbnailLoader(this)
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this)
        var viewManager = LinearLayoutManager(this)
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

        doAsync {
            Log.d("Main", "start")
            val resultPhotos = URL("http://jsonplaceholder.typicode.com/photos").readText()

            val arrPhoto = JSONArray(resultPhotos)
            for (i in 0 until 15) { // Walk through the Array.
                var resPhoto = Gson().fromJson(arrPhoto.getString(i), PhotoModel::class.java)
                images.add(resPhoto)
                Log.d("Main", "resTitle = " + resPhoto.title)
            }

            uiThread {
                toast("ready")
            }
        }

        doAsync {
            Log.d("Main", "start")
            val resultAlboms = URL("http://jsonplaceholder.typicode.com/albums").readText()

            val arrAlboms = JSONArray(resultAlboms)
            for (i in 0 until 15) { // Walk through the Array.
                var resAlbom = Gson().fromJson(arrAlboms.getString(i), AlbomModel::class.java)
                alboms.add(resAlbom)
                Log.d("Main", "resAlbom = " + resAlbom.title)
            }

            uiThread {
                toast("ready")
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun displayImagesInGallary(id : Int) {
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

        doAsync {
            val resultPhotos = URL("http://jsonplaceholder.typicode.com/photos").readText()

            val arrPhoto = JSONArray(resultPhotos)
            for (i in 0 until 15) { // Walk through the Array.
                var resPhoto = Gson().fromJson(arrPhoto.getString(i), PhotoModel::class.java)
                images.add(resPhoto)
                Log.d("Main", "resTitle = " + resPhoto.title)
            }

            uiThread {
                toast("ready")
                progressBar.visibility = View.GONE
                displayImagesInGallary(albomId)
            }
        }
        progressBar.visibility = View.VISIBLE
        val par = recyclerview2.layoutParams

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
