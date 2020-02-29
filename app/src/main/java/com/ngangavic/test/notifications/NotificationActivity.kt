package com.ngangavic.test.notifications

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ngangavic.test.R
import com.ngangavic.test.service.MyService

class NotificationActivity : AppCompatActivity() {

    lateinit var buttonSimpleNotification: Button
    lateinit var buttonExpandableNotification: Button
    lateinit var buttonNotificationAction: Button
    lateinit var buttonNotificationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        createNotificationChannel()

        buttonSimpleNotification = findViewById(R.id.buttonSimpleNotification)
        buttonExpandableNotification = findViewById(R.id.buttonExpandableNotification)
        buttonNotificationAction = findViewById(R.id.buttonNotificationAction)
        buttonNotificationButton = findViewById(R.id.buttonNotificationButton)

        buttonSimpleNotification.setOnClickListener {
            simpleNotification()
        }

        buttonExpandableNotification.setOnClickListener {
            createExpandableNotification()
        }

        buttonNotificationAction.setOnClickListener {
            createNotificationWithAction()
        }

        buttonNotificationButton.setOnClickListener {
            createNotificationWithButton()
        }
    }

    private fun createNotificationWithButton(){
        val playIntent = Intent(this, MyService::class.java)
        val playPendingIntent: PendingIntent =
                PendingIntent.getService(this, 0, playIntent, 0)
        val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText("Play some music? Click the button below.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_play, getString(R.string.play),
                        playPendingIntent)
        with(NotificationManagerCompat.from(this)) {
            notify(1000, builder.build())
        }
    }

    private fun createNotificationWithAction() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "I opened WhatsApp from a notification.");
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp")
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText("Click here to open WhatsApp.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(1000, builder.build())
        }
    }

    private fun createExpandableNotification() {
        var builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText("Google LLC is an American multinational technology company that specializes in Internet-related services and products, which include online advertising technologies, search engine, cloud computing, software, and hardware. It is considered one of the Big Four technology companies, alongside Amazon, Apple, and Facebook.")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Google LLC is an American multinational technology company that specializes in Internet-related services and products, which include online advertising technologies, search engine, cloud computing, software, and hardware. It is considered one of the Big Four technology companies, alongside Amazon, Apple, and Facebook."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(1000, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelId = "1"
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        Log.d("NOTIFICATION", "Notification channel created")
    }

    private fun simpleNotification() {
        var builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText("This is a simple notification. Google LLC is an American multinational technology company that specializes in Internet-related services and products, which include online advertising technologies, search engine, cloud computing, software, and hardware. It is considered one of the Big Four technology companies, alongside Amazon, Apple, and Facebook.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(1000, builder.build())
        }
    }


}
