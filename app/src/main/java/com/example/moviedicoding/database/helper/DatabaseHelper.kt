package com.example.moviedicoding.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviedicoding.database.table.FavoriteMovie
import com.example.moviedicoding.database.table.FavoriteMovie.MovieColumns.Companion.TABLE_NAME
import com.example.moviedicoding.database.table.FavoriteTv
import com.example.moviedicoding.database.table.FavoriteTv.TvColumns.Companion.TABLE_NAME_TV

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "dbmoviedicoding"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE_MOVIE = "CREATE TABLE $TABLE_NAME" +
                "(${FavoriteMovie.MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${FavoriteMovie.MovieColumns.MOVIE_ID} TEXT NOT NULL," +
                "${FavoriteMovie.MovieColumns.MOVIE_NAME} TEXT NOT NULL," +
                "${FavoriteMovie.MovieColumns.MOVIE_DETAIL} TEXT NOT NULL," +
                "${FavoriteMovie.MovieColumns.MOVIE_IMAGE} TEXT NOT NULL)"

        private val SQL_CREATE_TABLE_FAVORITE_TV = "CREATE TABLE $TABLE_NAME_TV" +
                "(${FavoriteTv.TvColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${FavoriteTv.TvColumns.TV_ID} TEXT NOT NULL," +
                "${FavoriteTv.TvColumns.TV_NAME} TEXT NOT NULL," +
                "${FavoriteTv.TvColumns.TV_DETAIL} TEXT NOT NULL," +
                "${FavoriteTv.TvColumns.TV_IMAGE} TEXT NOT NULL)"
    }

}