package com.example.nurseyit.gallerry.MainAvtivity.Alboms

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter
import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomModel
import com.example.nurseyit.gallerry.Model.AlbomModel.AlbomsAdapter
import com.example.nurseyit.gallerry.Model.AlbomModel.PhotoModel
import com.example.nurseyit.gallerry.Model.AlbomModel.RepositoryImpl
import com.example.nurseyit.gallerry.Presenter.AlbomPresenter.AlbomContract
import com.example.nurseyit.gallerry.Presenter.AlbomPresenter.PresenterImpl

import com.example.nurseyit.gallerry.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_blank.*


class BlankFragment : Fragment(), ImageGalleryAdapter.ImageThumbnailLoader,
        FullScreenImageGalleryAdapter.FullScreenImageLoader,
        AlbomsAdapter.OnItemClickInterface, AlbomContract.View {

    lateinit var presenter: AlbomContract.Presenter


    lateinit var viewAdapter: AlbomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerInstance.lifecycleOwner = this
        presenter = PresenterImpl(this, RepositoryImpl())
        initGallaryLib(this, this)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycleView()
        presenter.loadAlbom()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                BlankFragment()
    }

    private fun initRecycleView() {
        val viewManager = LinearLayoutManager(activity)
        viewAdapter = AlbomsAdapter(this)
        recyclerview3.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    private fun displayImagesInGallary( images: ArrayList<PhotoModel>) {
        val intent = Intent(activity, ImageGalleryActivity::class.java)
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
        progressBar3.visibility = View.VISIBLE
        recyclerview3.visibility = View.GONE
    }

    override fun closeProgressBar() {
        progressBar3.visibility = View.GONE
        recyclerview3.visibility = View.VISIBLE
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

