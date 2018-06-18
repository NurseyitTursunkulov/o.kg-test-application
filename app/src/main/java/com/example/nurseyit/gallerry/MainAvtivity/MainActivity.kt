package com.example.nurseyit.gallerry.MainAvtivity

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
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
import kotlin.collections.ArrayList
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.nurseyit.gallerry.Presenter.AlbomContract
import com.example.nurseyit.gallerry.Model.AlbomsAdapter
import com.example.nurseyit.gallerry.Model.*
import com.example.nurseyit.gallerry.Presenter.PresenterImpl
import com.example.nurseyit.gallerry.R
import com.example.nurseyit.gallerry.MainAvtivity.ownerInstance.lifecycleOwner
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity :AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val albumsFragment = BlankFragment.newInstance()
                openFragment(albumsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}