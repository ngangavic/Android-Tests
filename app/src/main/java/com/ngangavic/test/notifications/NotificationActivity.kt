package com.ngangavic.test.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ngangavic.test.R

class NotificationActivity : AppCompatActivity() {

    lateinit var buttonSimpleNotification:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        createNotificationChannel()

        buttonSimpleNotification=findViewById(R.id.buttonSimpleNotification)

        buttonSimpleNotification.setOnClickListener {
            simpleNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelId="1"
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        Log.d("NOTIFICATION","Notification channel created")
    }

    private fun simpleNotification(){
        var builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText("This is a simple notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(1000, builder.build())
        }
    }



}
