package com.ngangavic.test.workmanager.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ngangavic.test.R
import com.ngangavic.test.workmanager.Constants.Companion.CHANNEL_ID
import com.ngangavic.test.workmanager.Constants.Companion.DELAY_TIME_MILLIS
import com.ngangavic.test.workmanager.Constants.Companion.NOTIFICATION_ID
import com.ngangavic.test.workmanager.Constants.Companion.NOTIFICATION_TITLE
import com.ngangavic.test.workmanager.Constants.Companion.OUTPUT_PATH
import com.ngangavic.test.workmanager.Constants.Companion.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.ngangavic.test.workmanager.Constants.Companion.VERBOSE_NOTIFICATION_CHANNEL_NAME
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class WorkerUtils {
    companion object {
        fun makeStatusNotification(message: String, context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
                val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description
                val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

                notificationManager?.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(LongArray(0))
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }

        fun sleep() {
            try {
                Thread.sleep(DELAY_TIME_MILLIS, 0)
            } catch (e: InterruptedException) {
                Log.e("SLEEP-ERROR", e.toString())
            }
        }

        fun blurBitmap(bitmap: Bitmap, applicationContext: Context): Bitmap {
            lateinit var rsContext: RenderScript
            try {
                val output = Bitmap.createBitmap(
                        bitmap.width, bitmap.height, bitmap.config)
                rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
                val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
                val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
                val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
                theIntrinsic.apply {
                    setRadius(10f)
                    theIntrinsic.setInput(inAlloc)
                    theIntrinsic.forEach(outAlloc)
                }
                outAlloc.copyTo(output)

                return output
            } finally {
                rsContext.finish()
            }
        }

        fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
            val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
            val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
            if (!outputDir.exists()) {
                outputDir.mkdirs()
            }
            val outputFile = File(outputDir, name)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
            } finally {
                out?.let {
                    try {
                        it.close()
                    } catch (ignore: IOException) {
                    }

                }
            }
            return Uri.fromFile(outputFile)
        }
    }

}