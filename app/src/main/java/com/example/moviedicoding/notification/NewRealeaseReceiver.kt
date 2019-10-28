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
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.moviedicoding.R
import com.example.moviedicoding.activity.MainActivity
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_ID1
import com.example.moviedicoding.utils.UtilsConstant.Companion.CHANNEL_NAME
import com.example.moviedicoding.utils.UtilsConstant.Companion.GROUP_KEY
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewRealeaseReceiver: BroadcastReceiver() {
    private var NOTIF_ID_REPEATING = 1
    var movies = ArrayList<String>()

    internal fun setDailyNewMovies(context: Context) {
        val client = AsyncHttpClient()
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern);
        val date = simpleDateFormat.format(Date());
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=420abe5b406b7a21f3379e1b9ddaf925&primary_release_date.gte=$date&primary_release_date.lte=$date"
        println(url)
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        movies.add(movie.getString("title"))
                        sendNotification(context)

                        NOTIF_ID_REPEATING++
                    }

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    override fun onReceive(context: Context, intent: Intent) {
        setDailyNewMovies(context)
    }
    fun sendNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mBuilder: NotificationCompat.Builder
        if (NOTIF_ID_REPEATING < 20) {
            mBuilder = NotificationCompat.Builder(context,CHANNEL_ID1)
                .setSmallIcon(R.drawable.ic_notif_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                    R.drawable.ic_notif_foreground
                ))
                .setContentTitle("New Movie : " + movies.get(NOTIF_ID_REPEATING-1))
                .setContentText(movies.get(NOTIF_ID_REPEATING-1)+ " now has been release")
                .setSound(alarmSound)
                .setGroup(GROUP_KEY)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine("New Movie : " + movies.get(NOTIF_ID_REPEATING-1))
                .addLine("New Movie : " + movies.get(NOTIF_ID_REPEATING-2))
                .setBigContentTitle("$NOTIF_ID_REPEATING new movie")
                .setSummaryText("New Release Today")
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.ic_notif_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                    R.drawable.ic_notif_foreground
                ))
                .setContentTitle("$NOTIF_ID_REPEATING new movie")
                .setSound(alarmSound)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID1,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            mBuilder.setChannelId(CHANNEL_ID1)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(NOTIF_ID_REPEATING-1, notification)
    }
    fun setRepeatingAlarm(context: Context) {
            println("asd")
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NewRealeaseReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context,NOTIF_ID_REPEATING, intent, 0)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NewRealeaseReceiver::class.java)
        val pendingIntent = PendingIntent.getActivity(context, NOTIF_ID_REPEATING, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}