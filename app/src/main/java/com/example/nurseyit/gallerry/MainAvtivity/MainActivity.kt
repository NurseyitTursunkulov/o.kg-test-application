package com.example.nurseyit.gallerry.MainAvtivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.nurseyit.gallerry.MainAvtivity.Alboms.ImagesFragment
import com.example.nurseyit.gallerry.MainAvtivity.Posts.PostsFragment
import com.example.nurseyit.gallerry.R
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity :AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_images -> {
                val albumsFragment = ImagesFragment.newInstance()
                openFragment(albumsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_posts -> {
                val postsFragment = PostsFragment.newInstance()
                openFragment(postsFragment)
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