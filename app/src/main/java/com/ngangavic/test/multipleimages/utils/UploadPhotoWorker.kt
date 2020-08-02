package com.ngangavic.test.multipleimages.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class UploadPhotoWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            Log.e("WORKER", "STARTED")
//            inputData.toByteArray()
            val imageUriInput = getInputData().getString("imagePath")
//            val bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, Uri.parse(inputData.toString()))
////            bitmap=Bitmap.createScaledBitmap(bitmap, 720, 405, false);
//            val byteArrayOutputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
//            val data = byteArrayOutputStream.toByteArray()
            val storageRef = Firebase.storage.reference
            storageRef.child("android-test/multi-images/" + UUID.randomUUID().toString())
//            val uploadTask = storageRef.putBytes(data)
            val uploadTask = storageRef.putFile(Uri.parse(imageUriInput))
            uploadTask.addOnProgressListener { taskSnapshot ->
                Log.e("UPLOAD", taskSnapshot.bytesTransferred.toString() + "/" + taskSnapshot.totalByteCount.toString())
            }
                    .addOnSuccessListener { Log.e("UPLOAD", "Upload success") }
                    .addOnFailureListener { Log.e("UPLOAD", "Upload failed") }
            Result.success()
        } catch (throwable: Throwable) {
            Log.e("Error", throwable.toString())
            Result.failure()
        }
    }
}