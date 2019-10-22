package com.example.moviedicoding.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviedicoding.notification.DailyAlarmReceiver
import com.example.moviedicoding.R
import com.example.moviedicoding.fragment.FavoriteFragment
import com.example.moviedicoding.fragment.MovieFragment
import com.example.moviedicoding.fragment.TvShowFragment
import com.example.moviedicoding.model.Movie
import com.example.moviedicoding.model.NotificationItem
import com.example.moviedicoding.notification.NewRealeaseReceiver
import com.example.moviedicoding.utils.UtilsConstant.Companion.TYPE_REPEATING
import com.example.moviedicoding.viewmodel.MovieViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var dailyReceiver: DailyAlarmReceiver
    private lateinit var movieReceiver: NewRealeaseReceiver
    companion object{
        private val stackNotif = ArrayList<NotificationItem>()
        private val NOTIFICATION_REQUEST_CODE = 200
        private val MAX_NOTIFICATION = 3
        private var idNotification = 0
    }
    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            var fragment:Fragment
            when (item.getItemId()) {
                R.id.navigation_movie -> {
                    fragment = MovieFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
                R.id.navigation_tv -> {
                    fragment = TvShowFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
                R.id.navigation_favorite -> {
                    fragment = FavoriteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit();
                    return true
                }
            }
            return false
        }
    }
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_movie
        }
        dailyReceiver = DailyAlarmReceiver()
        movieReceiver = NewRealeaseReceiver()
        movieViewModel = MovieViewModel()

        movieViewModel.setDailyNewMovies()
        movieViewModel.getMovies().observe(this, Observer { movieItems ->
            if (movieItems!= null) {
                movieReceiver.setData(movieItems)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }else if(item.itemId == R.id.turn_on_notif){
            movieReceiver.setRepeatingAlarm(this)
            dailyReceiver.setRepeatingAlarm(this)
        }else if(item.itemId == R.id.turn_off_notif){
            movieReceiver.cancelAlarm(this)
            dailyReceiver.cancelAlarm(this)
        }
        return super.onOptionsItemSelected(item)
    }
}
