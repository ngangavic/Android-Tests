package com.ngangavic.test.jobscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ngangavic.test.MainActivity
import com.ngangavic.test.R


class NotificationJobService: JobService() {

    lateinit var notificationManager: NotificationManager
    var PRIMARY_CHANNEL_ID:String = "primary_notification_channel";

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartJob(params: JobParameters?): Boolean {
createNotificationChannel()
        val contentPendingIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job ran to completion!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)

        notificationManager.notify(0, builder.build())
        return false
    }

    fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,"Job Service notification",NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifications from Job Service"
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


}