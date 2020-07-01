package com.ngangavic.test.multipleimages.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ngangavic.test.R
import java.security.Provider

abstract class BaseTaskService: Service() {

    private var numTasks=0

    private val manager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun taskStarted(){
        changeNumberOfTasks(1)
    }

    fun taskCompleted(){
        changeNumberOfTasks(-1)
    }

    @Synchronized
    private fun changeNumberOfTasks(delta:Int){
        Log.d("TASK NUMBER ", "$numTasks $delta")
        numTasks +=delta

        if (numTasks<=0){
            Log.d("SERVICE","stopping")
            stopSelf()
        }
    }

    private fun createDefaultChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID_DEFAULT,
                    "Default",
                    NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
    }

    protected fun showProgressNotification(caption: String, completedUnits: Long, totalUnits: Long) {
        var percentComplete = 0
        if (totalUnits > 0) {
            percentComplete = (100 * completedUnits / totalUnits).toInt()
        }

        createDefaultChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_DEFAULT)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(caption)
                .setProgress(100, percentComplete, false)
                .setOngoing(true)
                .setAutoCancel(false)

        manager.notify(PROGRESS_NOTIFICATION_ID, builder.build())
    }

    protected fun showFinishedNotification(caption: String, intent: Intent, success: Boolean) {
        // Make PendingIntent for notification
        val pendingIntent = PendingIntent.getActivity(this, 0 /* requestCode */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val icon = if (success) R.drawable.ic_close else R.drawable.ic_delete

        createDefaultChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_DEFAULT)
                .setSmallIcon(icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(caption)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        manager.notify(FINISHED_NOTIFICATION_ID, builder.build())
    }

    protected fun dismissProgressNotification() {
        manager.cancel(PROGRESS_NOTIFICATION_ID)
    }

    companion object {

        private const val CHANNEL_ID_DEFAULT = "default"

        internal const val PROGRESS_NOTIFICATION_ID = 0
        internal const val FINISHED_NOTIFICATION_ID = 1

        private const val TAG = "MyBaseTaskService"
    }

}