package com.example.moviedicoding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedicoding.model.Movie
import com.example.moviedicoding.model.TvShow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "420abe5b406b7a21f3379e1b9ddaf925"
    }
    val listTvShows = MutableLiveData<ArrayList<TvShow>>()
    internal fun setTvShows() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"
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
                        val tv_shows = list.getJSONObject(i)
                        val tvShowsItem = TvShow()
                        val POSTER_FILENAME = tv_shows.getString("poster_path")
                        tvShowsItem.id = tv_shows.getInt("id")
                        tvShowsItem.image = "https://image.tmdb.org/t/p/w185$POSTER_FILENAME"
                        tvShowsItem.title = tv_shows.getString("original_name")
                        tvShowsItem.detail = tv_shows.getString("overview")
                        listItems.add(tvShowsItem)
                    }
                    listTvShows.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    internal fun getTvShows(): LiveData<ArrayList<TvShow>> {
        return listTvShows
    }
}