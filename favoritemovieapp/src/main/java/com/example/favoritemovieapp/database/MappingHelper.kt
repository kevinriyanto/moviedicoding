package com.example.favoritemovieapp.database

import android.database.Cursor
import com.example.favoriteapp.model.Movie

object MappingHelper {

    fun mapMovieCursorToArrayList(movieCursor: Cursor): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()

        while (movieCursor.moveToNext()) {
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_ID))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_NAME))
            val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_DETAIL))
            val image = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.MOVIE_IMAGE))
            movieList.add(Movie(image,title,description, id))
        }
        return movieList
    }
    fun mapTvCursorToArrayList(movieCursor: Cursor): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()

        while (movieCursor.moveToNext()) {
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_ID))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_NAME))
            val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_DETAIL))
            val image = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTVColumns.TV_IMAGE))
            movieList.add(Movie(image,title,description, id))
        }
        return movieList
    }
}