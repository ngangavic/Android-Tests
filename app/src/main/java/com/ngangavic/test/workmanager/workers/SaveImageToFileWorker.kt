package com.ngangavic.test.workmanager.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ngangavic.test.workmanager.Constants.Companion.KEY_IMAGE_URI
import com.ngangavic.test.workmanager.workers.WorkerUtils.Companion.makeStatusNotification
import com.ngangavic.test.workmanager.workers.WorkerUtils.Companion.sleep
import java.text.SimpleDateFormat
import java.util.*

class SaveImageToFileWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val Title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
            "yyyy.MM.dd 'at' HH:mm:ss z",
            Locale.getDefault()
    )

    override fun doWork(): Result {
        makeStatusNotification("Saving image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUrl = MediaStore.Images.Media.insertImage(
                    resolver, bitmap, Title, dateFormatter.format(Date()))
            if (!imageUrl.isNullOrEmpty()) {
                val output = workDataOf(KEY_IMAGE_URI to imageUrl)

                Result.success(output)
            } else {
                Log.e("SAVE-IMG-ERROR", "Writing to MediaStore failed")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("SAVE-IMG-ERROR", "Unable to save image to Gallery $e")
            Result.failure()
        }
    }
}