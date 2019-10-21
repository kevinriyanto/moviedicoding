package com.example.moviedicoding.database.table

import android.provider.BaseColumns

internal class FavoriteMovie{
    internal class MovieColumns : BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_movie"
            const val _ID = "_ID"
            const val MOVIE_ID = "MOVIE_ID"
            const val MOVIE_NAME = "MOVIE_NAME"
            const val MOVIE_IMAGE = "MOVIE_IMAGE"
            const val MOVIE_DETAIL = "MOVIE_DETAIL"
        }
    }
}