package com.ngangavic.test.workmanager


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.ngangavic.test.workmanager.Constants.Companion.IMAGE_MANIPULATION_WORK_NAME
import com.ngangavic.test.workmanager.Constants.Companion.KEY_IMAGE_URI
import com.ngangavic.test.workmanager.Constants.Companion.TAG_OUTPUT
import com.ngangavic.test.workmanager.workers.BlurWorker
import com.ngangavic.test.workmanager.workers.CleanupWorker
import com.ngangavic.test.workmanager.workers.SaveImageToFileWorker

class BlurViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager: WorkManager = WorkManager.getInstance(application)
    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    internal val outputWorkInfoItems: LiveData<List<WorkInfo>>

    init {
        outputWorkInfoItems = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    internal fun applyBlur(blurLevel: Int) {
        var continuation = workManager
                .beginUniqueWork(
                        IMAGE_MANIPULATION_WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(CleanupWorker::class.java)
                )
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }
            continuation = continuation.then(blurBuilder.build())
        }

        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .build()
        continuation = continuation.then(save)

        continuation.enqueue()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    internal fun setImageUri(uri: String?) {
        imageUri = uriOrNull(uri)
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

}