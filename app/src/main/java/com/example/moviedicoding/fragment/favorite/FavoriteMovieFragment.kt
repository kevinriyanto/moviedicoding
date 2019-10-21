package com.example.moviedicoding.fragment.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.moviedicoding.R
import com.example.moviedicoding.activity.DetailMovieActivity
import com.example.moviedicoding.adapter.ListMovieAdapter
import com.example.moviedicoding.database.helper.FavoriteMovieHelper
import com.example.moviedicoding.database.helper.MappingHelper
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment() {
    private lateinit var myHelper: FavoriteMovieHelper
    private lateinit var adapter: ListMovieAdapter
    private lateinit var pg:ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private fun loadMovieAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            pg.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                myHelper.open()
                val cursor = myHelper.queryAll()
                MappingHelper.mapMovieCursorToArrayList(cursor)
            }
            pg.visibility = View.INVISIBLE
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                adapter.setData(movies)
            } else {
                adapter.setData(ArrayList())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadMovieAsync()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_movie, container, false)
        myHelper = FavoriteMovieHelper.getInstance(view.context)
        myHelper.open()
        pg = view.progressBarFavorite
        adapter = ListMovieAdapter(){
            startActivity<DetailMovieActivity>("title" to it?.title,"image" to it?.image,"detail" to it?.detail, "id" to it?.id,"movie" to true)
        }
        adapter.notifyDataSetChanged()
        view.favorite_movie_list.layoutManager = LinearLayoutManager(context)
        view.favorite_movie_list.adapter = adapter
        swipeRefresh = view.swipe_favorite
        swipeRefresh.onRefresh {
            loadMovieAsync()
            swipeRefresh.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
        return view
    }
    override fun onDestroy() {
        super.onDestroy()
        myHelper.close()
    }


}
