package com.example.moviedicoding.activity

import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.moviedicoding.R
import com.example.moviedicoding.database.helper.FavoriteMovieHelper
import com.example.moviedicoding.database.helper.FavoriteTvHelper
import com.example.moviedicoding.database.table.FavoriteMovie
import com.example.moviedicoding.database.table.FavoriteTv
import com.example.moviedicoding.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.toast


class DetailMovieActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var pgBar:ProgressBar
    private lateinit var movieHelper:FavoriteMovieHelper
    private lateinit var tvHelper:FavoriteTvHelper
    companion object {
        const val RESULT_ADD = 101
    }

    private fun addToFavorite(){
        val movie_name = intent.getStringExtra("title")
        val movie_image = intent.getStringExtra("image")
        val movie_detail = intent.getStringExtra("detail")
        val movie_id = intent.getIntExtra("id",0)
        val movie = intent.getBooleanExtra("movie",false)

        var result:Long = 0
        if(movie){

            val values = ContentValues()
            values.put(FavoriteMovie.MovieColumns.MOVIE_NAME,movie_name)
            values.put(FavoriteMovie.MovieColumns.MOVIE_DETAIL,movie_detail)
            values.put(FavoriteMovie.MovieColumns.MOVIE_IMAGE,movie_image)
            values.put(FavoriteMovie.MovieColumns.MOVIE_ID,movie_id)
            result = movieHelper.insert(values)

        }else{

            val values = ContentValues()
            values.put(FavoriteTv.TvColumns.TV_NAME,movie_name)
            values.put(FavoriteTv.TvColumns.TV_DETAIL,movie_detail)
            values.put(FavoriteTv.TvColumns.TV_IMAGE,movie_image)
            values.put(FavoriteTv.TvColumns.TV_ID,movie_id)
            result = tvHelper.insert(values)
        }
        if (result > 0) {
            setResult(RESULT_ADD, intent)
            finish()
        } else {
            Toast.makeText(this@DetailMovieActivity, "Failed add favorite!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite)
    }
    private fun favoriteMovieState(){
        val movie_id = intent.getIntExtra("id",0)
        val result = movieHelper.queryMovieById(movie_id.toString())
        if(result.count > 0){
            isFavorite = true
        }

    }
    private fun favoriteTvState(){
        val movie_id = intent.getIntExtra("id",0)
        val result = tvHelper.queryMovieById(movie_id.toString())
        if(result.count > 0){
            isFavorite = true
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun removeFromFavorite(){
        val movie_id = intent.getIntExtra("id",0)
        val movie = intent.getBooleanExtra("movie",false)
        if(movie) movieHelper.deleteById(movie_id.toString())
        else tvHelper.deleteById(movie_id.toString())
        toast("Removed from favorite")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        menuItem = menu
        setFavorite()
        return true
    }
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
        val movie = intent.getBooleanExtra("movie",false)
        movieHelper = FavoriteMovieHelper.getInstance(applicationContext)
        movieHelper.open()
        tvHelper = FavoriteTvHelper.getInstance(applicationContext)
        tvHelper.open()


        isFavorite = false
        pgBar = progressBarDetail
        if(movie){
            favoriteMovieState()
        }else{
            favoriteTvState()
        }
        showLoading(true)
        val image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(movie_img_detail)
        detail_title.text = intent.getStringExtra("title")
        detail_desc.text =intent.getStringExtra("detail")
        showLoading(false)
    }


}
