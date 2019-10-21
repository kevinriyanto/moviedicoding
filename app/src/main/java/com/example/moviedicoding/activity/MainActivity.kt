package com.example.moviedicoding.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviedicoding.R
import com.example.moviedicoding.fragment.FavoriteFragment
import com.example.moviedicoding.fragment.MovieFragment
import com.example.moviedicoding.fragment.TvShowFragment
import com.example.moviedicoding.fragment.favorite.FavoriteMovieFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var fragment:Fragment
            when (item.getItemId()) {
                R.id.navigation_movie -> {
                    fragment = MovieFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
                R.id.navigation_tv -> {
                    fragment = TvShowFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
                R.id.navigation_favorite -> {
                    fragment = FavoriteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_movie
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
