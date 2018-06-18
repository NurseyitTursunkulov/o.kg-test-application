package com.example.nurseyit.gallerry.MainAvtivity

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
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.nurseyit.gallerry.Presenter.AlbomContract
import com.example.nurseyit.gallerry.Model.AlbomsAdapter
import com.example.nurseyit.gallerry.Model.*
import com.example.nurseyit.gallerry.Presenter.PresenterImpl
import com.example.nurseyit.gallerry.R
import com.example.nurseyit.gallerry.MainAvtivity.ownerInstance.lifecycleOwner

class MainActivity : AppCompatActivity(), ImageGalleryAdapter.ImageThumbnailLoader,
        FullScreenImageGalleryAdapter.FullScreenImageLoader,
        AlbomsAdapter.OnItemClickInterface, AlbomContract.View {

    lateinit var presenter: AlbomContract.Presenter


    lateinit var viewAdapter: AlbomsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleOwner = this
        presenter = PresenterImpl(this, RepositoryImpl())
        initGallaryLib(this, this)
        initRecycleView()
        presenter.loadAlbom()

    }



    private fun initRecycleView() {
       val viewManager = LinearLayoutManager(this)
        viewAdapter = AlbomsAdapter(this)
        recyclerview2.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    private fun displayImagesInGallary( images: ArrayList<PhotoModel>) {
        val intent = Intent(this@MainActivity, ImageGalleryActivity::class.java)
        Log.d("Main", " images =  " + images.toString())
        val bundle = Bundle()
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, images.url())
        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images")
        intent.putExtras(bundle)

        startActivity(intent)
    }

    override fun onClick(albomId: Int) {
        presenter.loadImagesToGallary(albomId)
    }


    override fun showAlbom(listAlbom: List<AlbomModel>) {
        viewAdapter.myDataset = listAlbom as ArrayList<AlbomModel>
        viewAdapter.notifyDataSetChanged()
    }

    override fun showGallary(photoList: List<PhotoModel>) {
        displayImagesInGallary(photoList as ArrayList<PhotoModel>)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        recyclerview2.visibility = View.GONE
    }

    override fun closeProgressBar() {
        progressBar.visibility = View.GONE
        recyclerview2.visibility = View.VISIBLE
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
