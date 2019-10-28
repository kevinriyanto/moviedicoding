package com.example.moviedicoding.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.moviedicoding.R
import com.example.moviedicoding.notification.DailyAlarmReceiver
import com.example.moviedicoding.notification.NewRealeaseReceiver
import org.jetbrains.anko.support.v4.toast

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String?) {
        val isChecked = p0.getBoolean("notifications",false)
        if(isChecked){
            movieReceiver.setRepeatingAlarm(requireContext())
            dailyReceiver.setRepeatingAlarm(requireContext())
        }else{
            movieReceiver.cancelAlarm(requireContext())
            dailyReceiver.cancelAlarm(requireContext())
        }
        toast("ischecked"+isChecked)
    }
    private lateinit var dailyReceiver: DailyAlarmReceiver
    private lateinit var movieReceiver: NewRealeaseReceiver
    private lateinit var state: SwitchPreferenceCompat
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root, rootKey)
        state = findPreference("notifications")
        dailyReceiver = DailyAlarmReceiver()
        movieReceiver = NewRealeaseReceiver()

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
