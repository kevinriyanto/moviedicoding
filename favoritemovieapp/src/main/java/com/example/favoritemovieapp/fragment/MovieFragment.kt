package com.example.favoritemovieapp.fragment


import android.annotation.SuppressLint
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoriteapp.model.Movie
import com.example.favoritemovieapp.R
import com.example.favoritemovieapp.database.DatabaseContract
import com.example.favoritemovieapp.database.MappingHelper
import com.example.moviedicoding.adapter.ListMovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
    private lateinit var adapter: ListMovieAdapter
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.movies)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadMovieAsync()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        view.favorite_consum_list.layoutManager = LinearLayoutManager(context)
        view.favorite_consum_list.setHasFixedSize(true)
        adapter = ListMovieAdapter {

        }
        view.favorite_consum_list.adapter = adapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadMovieAsync()
            }
        }
        context?.contentResolver?.registerContentObserver(DatabaseContract.FavoriteColumns.CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadMovieAsync()
        }
        else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) {
                adapter.setData(list)
            }
        }
        return view
    }

    private lateinit var cursor:Cursor
    @SuppressLint("Recycle")
    private fun loadMovieAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                cursor = context?.contentResolver?.query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, null, null, null) as Cursor
                MappingHelper.mapMovieCursorToArrayList(cursor)
            }
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                adapter.setData(movies)
            } else {
                adapter.setData(ArrayList())
            }
            cursor.close()
        }

    }


}
