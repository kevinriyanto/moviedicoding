package com.example.moviedicoding.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedicoding.R
import com.example.moviedicoding.activity.DetailMovieActivity
import com.example.moviedicoding.adapter.ListMovieAdapter
import com.example.moviedicoding.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.jetbrains.anko.support.v4.startActivity


class MovieFragment : Fragment() {
    private lateinit var adapter:ListMovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var pgBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieViewModel::class.java)

    }
    fun showLoading(state:Boolean){
        if (state){
            pgBar.visibility = View.VISIBLE
        }else{
            pgBar.visibility = View.GONE
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        pgBar = view.progressBar
        adapter =  ListMovieAdapter(){
            startActivity<DetailMovieActivity>("title" to it?.title,"image" to it?.image,"detail" to it?.detail, "id" to it?.id,"movie" to true)
        }
        adapter.notifyDataSetChanged()
        view.movie_list.layoutManager = LinearLayoutManager(context)
        view.movie_list.adapter = adapter
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        showLoading(true)

        if(isConnected){
            movieViewModel.setMovies()
        }else{
            showLoading(false)
        }

        movieViewModel.getMovies().observe(this, Observer { movieItems ->
            if (movieItems!= null) {
                adapter.setData(movieItems)
                showLoading(false)
            }
        })

        return view
    }

}
