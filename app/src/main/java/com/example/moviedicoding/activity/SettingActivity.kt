package com.example.moviedicoding.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedicoding.R
import com.example.moviedicoding.fragment.SettingFragment

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings,SettingFragment())
            .commit()
    }
}
