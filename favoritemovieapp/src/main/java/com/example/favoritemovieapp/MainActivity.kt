package com.example.favoritemovieapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.favoritemovieapp.fragment.MovieFragment
import com.example.favoritemovieapp.fragment.TvFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var fragment: Fragment
            when (item.getItemId()) {
                R.id.navigation_movie -> {
                    fragment = MovieFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
                R.id.navigation_tv -> {
                    fragment = TvFragment()
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



}
