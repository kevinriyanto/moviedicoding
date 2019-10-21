package com.example.moviedicoding.database.helper

import android.database.Cursor
import com.example.moviedicoding.database.table.FavoriteMovie
import com.example.moviedicoding.database.table.FavoriteTv
import com.example.moviedicoding.model.Movie
import com.example.moviedicoding.model.TvShow

object MappingHelper {

    fun mapMovieCursorToArrayList(movieCursor: Cursor): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()

        while (movieCursor.moveToNext()) {
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(FavoriteMovie.MovieColumns.MOVIE_ID))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteMovie.MovieColumns.MOVIE_NAME))
            val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteMovie.MovieColumns.MOVIE_DETAIL))
            val image = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteMovie.MovieColumns.MOVIE_IMAGE))
            movieList.add(Movie(image,title,description, id))
        }
        return movieList
    }
    fun mapTvCursorToArrayList(movieCursor: Cursor): ArrayList<TvShow> {

        val movieList = ArrayList<TvShow>()

        while (movieCursor.moveToNext()) {
            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(FavoriteTv.TvColumns.TV_ID))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteTv.TvColumns.TV_NAME))
            val description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteTv.TvColumns.TV_DETAIL))
            val image = movieCursor.getString(movieCursor.getColumnIndexOrThrow(FavoriteTv.TvColumns.TV_IMAGE))
            movieList.add(TvShow(image,title,description, id))
        }
        return movieList
    }
}