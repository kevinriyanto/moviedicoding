package com.example.moviedicoding.adapter

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviedicoding.R
import com.example.moviedicoding.fragment.favorite.FavoriteMovieFragment
import com.example.moviedicoding.fragment.favorite.FavoriteTvFragment

class MyPagerAdapter(fm: FragmentManager,context: Context) : FragmentPagerAdapter(fm) {
    private var ctx: Context
    init {
        ctx = context
    }
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoriteMovieFragment()
            }
            else -> {
                return FavoriteTvFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> ctx.getString(R.string.nav_title_movie)
            else -> {
                return ctx.getString(R.string.nav_title_tv_show)
            }
        }
    }
}