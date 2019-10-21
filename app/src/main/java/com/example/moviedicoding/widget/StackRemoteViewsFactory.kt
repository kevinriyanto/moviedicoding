package com.example.moviedicoding.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviedicoding.R
import com.example.moviedicoding.database.helper.FavoriteMovieHelper
import com.example.moviedicoding.database.helper.FavoriteTvHelper
import com.example.moviedicoding.database.helper.MappingHelper

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory{
    override fun onCreate() {
        movieHelper = FavoriteMovieHelper.getInstance(mContext)
    }

    override fun onDestroy() {
    }
    private val mWidgetItems = ArrayList<String?>()

    override fun getCount(): Int = mWidgetItems.size
    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0
    private lateinit var movieHelper: FavoriteMovieHelper
    private lateinit var tvHelper: FavoriteTvHelper
    private lateinit var cursor: Cursor
    fun loadMovie(){
        movieHelper = FavoriteMovieHelper.getInstance(mContext)
        movieHelper.open()
        cursor = movieHelper.queryAll()
        val identityToken = Binder.clearCallingIdentity()
        val movies = MappingHelper.mapMovieCursorToArrayList(cursor)
        for (movie in movies){
            mWidgetItems.add(movie.image)
        }
        tvHelper = FavoriteTvHelper.getInstance(mContext)
        tvHelper.open()
        cursor = tvHelper.queryAll()
        val movies1 = MappingHelper.mapTvCursorToArrayList(cursor)
        for (movie in movies1){
            mWidgetItems.add(movie.image)
        }
        Binder.restoreCallingIdentity(identityToken)
    }
    override fun onDataSetChanged() {
        loadMovie()
        println("asdasd" + cursor.count)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        val bitmap = Glide.with(mContext)
            .asBitmap()
            .load(mWidgetItems.get(position))
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().transform(RoundedCorners(48)))
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)
        val extras = bundleOf(
            MovieStackWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }


    override fun getViewTypeCount(): Int = 1

}