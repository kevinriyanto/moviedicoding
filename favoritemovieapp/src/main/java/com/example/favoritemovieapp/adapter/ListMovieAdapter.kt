package com.example.moviedicoding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favoriteapp.model.Movie
import com.example.favoritemovieapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_row.view.*

class ListMovieAdapter(private val listener:(
    Movie?)-> Unit): RecyclerView.Adapter<ListMovieAdapter.MoviesViewHolder>(){

    val movies = ArrayList<Movie>()
    fun setData(items: ArrayList<Movie>) {
        movies.clear()
        movies.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoviesViewHolder {
        return MoviesViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_movie_row,p0,false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(p0: MoviesViewHolder, p1: Int) {
        p0.bind(movies[p1], listener)
    }
    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(movies: Movie, listener: (Movie?)-> Unit){
            with(itemView){
                movies.image?.let { Picasso.get().load(it).into(img_item_photo) }
                title_movie.text = movies.title
                setOnClickListener {
                    listener(movies)
                }
            }
        }
    }

}