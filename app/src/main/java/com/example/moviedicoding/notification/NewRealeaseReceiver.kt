package com.example.moviedicoding.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.example.moviedicoding.activity.MainActivity
import java.util.*
import com.example.moviedicoding.R
import com.example.moviedicoding.model.Movie
import com.example.moviedicoding.model.NotificationItem
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_ID1
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_NAME
import com.example.moviedicoding.utils.UtilsConstant.Companion.GROUP_KEY
import com.example.moviedicoding.utils.UtilsConstant.Companion.ID_REPEATING1
import kotlin.collections.ArrayList


class NewRealeaseReceiver: BroadcastReceiver() {
    private val NOTIF_ID_REPEATING = 1
    var movies = ArrayList<Movie>()
    fun setData(items: ArrayList<Movie>) {
        movies.clear()
        movies.addAll(items)
    }
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("movie_title")
        val id = intent.getIntExtra("id",0)
            sendNotification(context,title)
    }
    fun sendNotification(context: Context,title:String) {
        println("new realease"+ title)
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mBuilder = NotificationCompat.Builder(context,CHANNEL_ID1)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notif_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                    R.drawable.ic_notif_foreground
                ))
                .setContentTitle("New Relase")
                .setContentText("Movie $title released")
                .setSubText(context.resources.getString(R.string.Reminder))
                .setSound(alarmSound)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID1,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            mBuilder.setChannelId(CHANNEL_ID1)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(NOTIF_ID_REPEATING, notification)
    }
    var notifId = 1000
    fun setRepeatingAlarm(context: Context) {
        var delay = 0
        for(movie in movies){

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NewRealeaseReceiver::class.java)
            intent.putExtra("movie_title",movie.title)
            intent.putExtra("id",notifId)
            val pendingIntent = PendingIntent.getBroadcast(context,NOTIF_ID_REPEATING, intent, 0)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            println("sizemovie"+movie.title)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + delay, AlarmManager.INTERVAL_DAY, pendingIntent)
            delay += 3000
            notifId++
        }
    }
    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NewRealeaseReceiver::class.java)
        val pendingIntent = PendingIntent.getActivity(context, NOTIF_ID_REPEATING, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}