package com.ngangavic.test.workmanager.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ngangavic.test.R
import com.ngangavic.test.workmanager.workers.WorkerUtils.Companion.blurBitmap
import com.ngangavic.test.workmanager.workers.WorkerUtils.Companion.makeStatusNotification
import com.ngangavic.test.workmanager.workers.WorkerUtils.Companion.writeBitmapToFile

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        makeStatusNotification("Blurring image", applicationContext)
        return try {
            val picture = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.couple_image)
            val output = blurBitmap(picture, applicationContext)
            val outputUri = writeBitmapToFile(applicationContext, output)
            makeStatusNotification("Output is $outputUri", applicationContext)
            Result.success()
        } catch (throwable: Throwable) {
            Log.e("Error", throwable.toString())
            Result.failure()
        }

    }

}