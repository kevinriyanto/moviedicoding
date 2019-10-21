package com.example.moviedicoding.database.table

import android.provider.BaseColumns
internal class FavoriteTv {
    internal class TvColumns : BaseColumns {
        companion object {
            const val TABLE_NAME_TV = "favorite_tv"
            const val _ID = "_ID"
            const val TV_ID = "TV_ID"
            const val TV_NAME = "TV_NAME"
            const val TV_IMAGE = "TV_IMAGE"
            const val TV_DETAIL = "TV_DETAIL"
        }
    }
}