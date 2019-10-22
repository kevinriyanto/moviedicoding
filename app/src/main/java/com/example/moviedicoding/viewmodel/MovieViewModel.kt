package com.example.moviedicoding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedicoding.model.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "420abe5b406b7a21f3379e1b9ddaf925"
    }
    val listMovies = MutableLiveData<ArrayList<Movie>>()
    internal fun setMovies() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"
//        println(url)
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val moviesItem = Movie()
                        val POSTER_FILENAME = movie.getString("poster_path")
                        moviesItem.id = movie.getInt("id")
                        moviesItem.image = "https://image.tmdb.org/t/p/w185$POSTER_FILENAME"
                        moviesItem.title = movie.getString("title")
                        moviesItem.detail = movie.getString("overview")
                        listItems.add(moviesItem)
                    }
                    listMovies.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    internal fun setSearchMovies(query: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()

        val url = "https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&language=en-US&query=$query"
//        println(url)
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val moviesItem = Movie()
                        val POSTER_FILENAME = movie.getString("poster_path")
                        moviesItem.id = movie.getInt("id")
                        moviesItem.image = "https://image.tmdb.org/t/p/w185$POSTER_FILENAME"
                        moviesItem.title = movie.getString("title")
                        moviesItem.detail = movie.getString("overview")
                        listItems.add(moviesItem)
                    }
                    listMovies.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    internal fun setDailyNewMovies() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern);
        val date = simpleDateFormat.format(Date());
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&primary_release_date.gte=$date&primary_release_date.lte=$date"
        println(url)
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val moviesItem = Movie()
                        val POSTER_FILENAME = movie.getString("poster_path")
                        moviesItem.id = movie.getInt("id")
                        moviesItem.image = "https://image.tmdb.org/t/p/w185$POSTER_FILENAME"
                        moviesItem.title = movie.getString("title")
                        moviesItem.detail = movie.getString("overview")
                        listItems.add(moviesItem)
                    }
                    listMovies.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}