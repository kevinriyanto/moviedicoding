package com.example.moviedicoding.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviedicoding.R
import com.example.moviedicoding.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*


class DetailMovieActivity : AppCompatActivity() {

    private lateinit var pgBar:ProgressBar
    fun showLoading(state:Boolean){
        if (state){
            pgBar.visibility = View.VISIBLE
        }else{
            pgBar.visibility = View.GONE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        pgBar = progressBarDetail
        showLoading(true)
        val image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(movie_img_detail)
        detail_title.text = intent.getStringExtra("title")
        detail_desc.text =intent.getStringExtra("detail")
        showLoading(false)


    }
}
