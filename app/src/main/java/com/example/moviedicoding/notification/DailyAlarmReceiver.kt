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
import androidx.core.app.NotificationCompat
import com.example.moviedicoding.activity.MainActivity
import java.util.*
import com.example.moviedicoding.R
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_ID
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_NAME
import com.example.moviedicoding.utils.UtilsConstant.Companion.GROUP_KEY
import com.example.moviedicoding.utils.UtilsConstant.Companion.ID_REPEATING


class DailyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context)
    }

    fun sendNotification(context: Context) {
        println("daily")
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mBuilder = NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notif_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                R.drawable.ic_notif_foreground
            ))
            .setContentTitle(context.resources.getString(R.string.Reminder))
            .setContentText(context.resources.getString(R.string.subtext))
            .setSubText(context.resources.getString(R.string.Reminder))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            mBuilder.setChannelId(CHANNEL_ID)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(ID_REPEATING, notification)
    }
    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyAlarmReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context,
            ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getActivity(context, ID_REPEATING, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}