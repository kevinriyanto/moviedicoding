package com.example.moviedicoding.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviedicoding.R
import com.example.moviedicoding.activity.DetailMovieActivity
import com.example.moviedicoding.adapter.ListTvShowAdapter

import com.example.moviedicoding.model.TvShow
import com.example.moviedicoding.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_tvshow.view.*
import org.jetbrains.anko.support.v4.startActivity
class TvShowFragment : Fragment() {
    private lateinit var adapter:ListTvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var pgBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel::class.java)

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
        val view = inflater.inflate(R.layout.fragment_tvshow, container, false)
        pgBar = view.progressBarTV

        adapter =  ListTvShowAdapter(){
            startActivity<DetailMovieActivity>("title" to it?.title,"image" to it?.image,"detail" to it?.detail)
        }
        adapter.notifyDataSetChanged()
        view.tv_show_list.layoutManager = LinearLayoutManager(context)
        view.tv_show_list.adapter = adapter
        tvShowViewModel.setTvShows()
        showLoading(true)

        tvShowViewModel.getTvShows().observe(this, Observer { movieItems ->
            if (movieItems!= null) {
                adapter.setData(movieItems)
                showLoading(false)
            }
        })
        return view
    }

}