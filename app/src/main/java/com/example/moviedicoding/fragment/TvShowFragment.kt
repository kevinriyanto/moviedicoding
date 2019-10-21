package com.example.moviedicoding.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviedicoding.R
import com.example.moviedicoding.activity.DetailMovieActivity
import com.example.moviedicoding.activity.MainActivity
import com.example.moviedicoding.adapter.ListTvShowAdapter

import com.example.moviedicoding.model.TvShow
import com.example.moviedicoding.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_tvshow.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class TvShowFragment : Fragment() {
    private lateinit var adapter:ListTvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var pgBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel::class.java)
        setHasOptionsMenu(true)

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
            startActivity<DetailMovieActivity>("title" to it?.title,"image" to it?.image,"detail" to it?.detail, "id" to it?.id, "tv" to true)
        }
        adapter.notifyDataSetChanged()
        view.tv_show_list.layoutManager = LinearLayoutManager(context)
        view.tv_show_list.adapter = adapter

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        showLoading(true)

        if(isConnected){
            tvShowViewModel.setTvShows()
        }else{
            showLoading(false)
        }

        tvShowViewModel.getTvShows().observe(this, Observer { movieItems ->
            if (movieItems!= null) {
                adapter.setData(movieItems)
                showLoading(false)
            }
        })
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_menu,menu)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        val searchItem:MenuItem = menu.findItem(R.id.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                tvShowViewModel.setSearchTvShows(query)
                tvShowViewModel.getTvShows().observe(this@TvShowFragment, Observer { movieItems ->
                    if (movieItems!= null) {
                        adapter.setData(movieItems)
                        showLoading(false)
                    }
                })
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        searchView.setOnClickListener { view -> }
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if(!p1){
                    searchItem.collapseActionView()
                    tvShowViewModel.setTvShows()
                    showLoading(true)
                    tvShowViewModel.getTvShows().observe(this@TvShowFragment, Observer { movieItems ->
                        if (movieItems!= null) {
                            adapter.setData(movieItems)
                            showLoading(false)
                        }
                    })
                }
            }
        })
    }

}
