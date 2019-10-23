package com.example.moviedicoding.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.moviedicoding"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_movie"
            const val _ID = "_ID"
            const val MOVIE_ID = "MOVIE_ID"
            const val MOVIE_NAME = "MOVIE_NAME"
            const val MOVIE_IMAGE = "MOVIE_IMAGE"
            const val MOVIE_DETAIL = "MOVIE_DETAIL"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
    class FavoriteTVColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_tv"
            const val _ID = "_ID"
            const val TV_ID = "TV_ID"
            const val TV_NAME = "TV_NAME"
            const val TV_IMAGE = "TV_IMAGE"
            const val TV_DETAIL = "TV_DETAIL"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}