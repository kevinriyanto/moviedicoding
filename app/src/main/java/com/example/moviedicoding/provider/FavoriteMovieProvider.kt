package com.example.moviedicoding.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.moviedicoding.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.moviedicoding.database.helper.FavoriteMovieHelper
import com.example.moviedicoding.database.helper.FavoriteTvHelper
import com.example.moviedicoding.database.table.FavoriteMovie.MovieColumns.Companion.TABLE_NAME



class FavoriteMovieProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private const val TV = 3
        private const val TV_ID = 4
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: FavoriteMovieHelper
        private lateinit var tvHelper: FavoriteTvHelper
        init {
            sUriMatcher.addURI("com.example.moviedicoding", TABLE_NAME, MOVIE)
            sUriMatcher.addURI("com.example.moviedicoding",
                "$TABLE_NAME/*",
                MOVIE_ID)
            sUriMatcher.addURI("com.example.moviedicoding",
                "favorite_tv",
                TV
            )
            sUriMatcher.addURI("com.example.moviedicoding",
                "favorite_tv/*",
                TV_ID
            )
        }
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (MOVIE) {
            sUriMatcher.match(uri) -> movieHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        movieHelper = FavoriteMovieHelper.getInstance(context as Context)
        movieHelper.open()
        tvHelper = FavoriteTvHelper.getInstance(context as Context)
        tvHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = movieHelper.queryAll()
            MOVIE_ID -> cursor = movieHelper.queryMovieById(uri.lastPathSegment.toString())
            TV -> cursor = tvHelper.queryAll()
            TV_ID -> cursor = tvHelper.queryMovieById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }
}
