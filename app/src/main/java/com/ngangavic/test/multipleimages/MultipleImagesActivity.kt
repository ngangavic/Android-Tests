package com.ngangavic.test.multipleimages

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.JobIntentService.enqueueWork
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.ngangavic.test.R
import com.ngangavic.test.multipleimages.utils.UploadPhotoService
import com.ngangavic.test.multipleimages.utils.UploadPhotoWorker
import com.ngangavic.test.workmanager.Constants
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MultipleImagesActivity : AppCompatActivity() {

    private lateinit var buttonSelect: Button
    private lateinit var buttonUpload: Button
    private lateinit var gridView: GridView
    private lateinit var progressBar: ProgressBar
    private lateinit var caption: TextView

    lateinit var filePaths: ArrayList<Uri>

    lateinit var galleryAdapter: GalleryAdapter
    lateinit var buttonAlert: Button

    lateinit var storage: FirebaseStorage
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var broadcastReceiver: BroadcastReceiver

    private var downloadUrl: Uri? = null
    private lateinit var fileUri: ArrayList<Uri>
    private lateinit var fileStirngPaths: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_images)

        buttonSelect = findViewById(R.id.buttonSelect)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonAlert = findViewById(R.id.buttonAlert)
        gridView = findViewById(R.id.gridView)
        progressBar = findViewById(R.id.progressBar)
        caption = findViewById(R.id.caption)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        requestPermissions()
        filePaths = ArrayList()
        fileUri = ArrayList()
        fileStirngPaths = ArrayList()
        buttonSelect.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**The following line is the important one!
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            startActivityForResult(intent,  100)
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 100)
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)
//            fileUri.clear()
//            filePaths.clear()
//            FilePickerBuilder.instance
//                    .setMaxCount(5) //optional
//                    .setSelectedFiles(filePaths) //optional
//                    .setActivityTheme(R.style.LibAppTheme) //optional
//                    .pickPhoto(this)
        }

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            galleryAdapter.removeItem(position)
            Toast.makeText(applicationContext, "Removed item at : $position", Toast.LENGTH_LONG).show()
        }

        buttonAlert.setOnClickListener {
            val multipleImagesDialog = MultipleImagesDialog(fileUri).newInstance()
            multipleImagesDialog.isCancelable = false
            multipleImagesDialog.show(supportFragmentManager, "dialog grid view")
        }

//        buttonUpload.setOnClickListener { uploadToFirebase(filePaths) }
        buttonUpload.setOnClickListener { upload(fileUri) }

    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.getContentResolver().query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path.toString()
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    private fun createInputData(imagePath: String): Data {
        return Data.Builder()
                .putString("imagePath", imagePath)
                .build()
    }


    private fun upload(uri: ArrayList<Uri>){
        Log.e("DATA LIST", uri.toString())
        for(i in uri) {
            Log.e("DATA LIST I", i.toString())
            val mIntent = Intent(this, UploadPhotoService::class.java)
            mIntent.putExtra("photo", i.toString())
            val uploadPhotoService = UploadPhotoService()
            uploadPhotoService.enqueueWork(this, mIntent)

        }
//        val workManager: WorkManager = WorkManager.getInstance(application)
//        var continuation = workManager
//                .beginUniqueWork(
//                        Constants.IMAGE_MANIPULATION_WORK_NAME,
//                        ExistingWorkPolicy.REPLACE,
//                        OneTimeWorkRequest.from(UploadPhotoWorker::class.java)
//                )

//        for (i in uri) {
//            val oneTimeWorkRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(UploadPhotoWorker::class.java)
//                    .setInputData(createInputData(uri[0].toString()))
//                    .setInitialDelay(2, TimeUnit.SECONDS).build()
//            workManager.enqueue(oneTimeWorkRequest)
//            val uploadBuilder: Data.Builder = Data.Builder()
//            uploadBuilder.putString("image_uri", i.toString())
//            val ImageUriInputData = uploadBuilder.build()
//
//            Log.e("WORKER","UPLOADING")
//            val blurBuilder = OneTimeWorkRequestBuilder<UploadPhotoWorker>()
//            blurBuilder.setInputData(ImageUriInputData)
//            continuation = continuation.then(blurBuilder.build())
//            val mServiceIntent = Intent(this, UploadPhotoService::class.java)
//            mServiceIntent.putExtra("data", i)
//            startService(mServiceIntent)
//        }
//        val constraints = Constraints.Builder()
//                .setRequiresCharging(true)
//                .build()
//        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
//                .setConstraints(constraints)
//                .addTag(Constants.TAG_OUTPUT)
//                .build()
//        continuation = continuation.then(save)

//        continuation.enqueue()
    }

    private fun createInputDataForUri(uri:Uri): Data {
        val builder = Data.Builder()
        uri.let {
            builder.putString(Constants.KEY_IMAGE_URI, uri.toString())
        }
        return builder.build()
    }

    private fun uploadToFirebase(filePaths: ArrayList<Uri>) {
        Toast.makeText(applicationContext, filePaths.size.toString(), Toast.LENGTH_SHORT).show()
        var img = 0
        for (i in filePaths) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, i)
//            bitmap=Bitmap.createScaledBitmap(bitmap, 720, 405, false);
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()
            val storageRef = storage.reference.child("android-test/multi-images/" + UUID.randomUUID().toString())
            val uploadTask = storageRef.putBytes(data)
            uploadTask.continueWithTask(object : Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                override fun then(p0: Task<UploadTask.TaskSnapshot>): Task<Uri> {
                    if (!p0.isSuccessful) {
                        p0.exception?.let {
                            throw it
                        }
                    }
                    return storageRef.getDownloadUrl()
                }
            }).addOnCompleteListener(object : OnCompleteListener<Uri> {
                override fun onComplete(p0: Task<Uri>) {
                    if (p0.isSuccessful()) {
                        val downloadUri = p0.getResult()
                        Log.d("DOWNLOAD URi", downloadUri.toString())
                        val url = downloadUri.toString()
                        Log.d("URL" + img++, downloadUri.toString())
//                        database.child("android-test").child(editTextName.text.toString().replace(" ", "")).child("url").setValue(downloadUri.toString())
                        Toast.makeText(applicationContext, "Success!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            when (requestCode) {
                100 -> {
                    if (resultCode == RESULT_OK && data != null) {
                        if (data.clipData != null) {
                            val count = data.clipData!!.itemCount
                            for (i in count until count) {
                                val imageUri = data.clipData!!.getItemAt(i).uri
//                            fileStirngPaths.add(getRealPathFromURIPath(imageUri, this))
                                fileUri.add(imageUri)
                            }
//                            galleryAdapter = GalleryAdapter(applicationContext, fileUri)
//                            gridView.verticalSpacing = gridView.horizontalSpacing
//                            val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
//                            marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
//                            gridView.adapter = galleryAdapter
                        } else if (data.getData() != null) {
                            val imagePath = Uri.parse(data.data!!.path)
//                        fileStirngPaths.add(getRealPathFromURIPath(imagePath, this))
                            fileUri.add(imagePath)
//                            galleryAdapter = GalleryAdapter(applicationContext, fileUri)
//                            gridView.verticalSpacing = gridView.horizontalSpacing
//                            val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
//                            marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
//                            gridView.adapter = galleryAdapter
                        }
                    }
                }
//            FilePickerConst.REQUEST_CODE_PHOTO -> {
//                val dataList = data!!.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
////                val dataList = data!!.getParcelableExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
//
//                if (resultCode == RESULT_OK && data != null) {
//                    fileUri = dataList
//                    Log.d("DATALIST: ", data.toString())
////                    for (path in dataList) {
////                        Log.d("DATAPATH: ", listOf(path).toString())
////                        filePaths.addAll(listOf(path))
////                    }
//                    galleryAdapter = GalleryAdapter(applicationContext, filePaths)
//                    gridView.verticalSpacing = gridView.horizontalSpacing
//                    val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
//                    marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
//                    gridView.adapter = galleryAdapter
//                }
//
//
//            }
            }
        }catch (e:Exception){
            Log.e("IMG",e.message.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun showMessageDialog(title: String, message: String) {
        val ad = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create()
        ad.show()
    }

    private fun showProgressBar(progressCaption: String) {
        caption.text = progressCaption
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        caption.text = ""
        progressBar.visibility = View.INVISIBLE
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            300 -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED

                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]

                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                    ) {
                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            showDialogOK("Service Permissions are required for this app",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> requestPermissions()
                                            DialogInterface.BUTTON_NEGATIVE ->
                                                finish()
                                        }
                                    })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                        }
                    }
                }
            }
        }
    }

    private fun requestPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        val listPermissionsNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(),
                    300
            )
            return false
        }
        return true
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    startActivity(
                            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.ngangavic.test")))
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

}
