package com.ngangavic.test.multipleimages

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class MultipleImagesActivity : AppCompatActivity() {

    private lateinit var buttonSelect: Button
    private lateinit var buttonUpload: Button
    private lateinit var gridView: GridView
    lateinit var filePaths: ArrayList<Uri>
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var buttonAlert: Button
    lateinit var storage: FirebaseStorage
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_images)

        buttonSelect = findViewById(R.id.buttonSelect)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonAlert = findViewById(R.id.buttonAlert)
        gridView = findViewById(R.id.gridView)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        filePaths = ArrayList()
        buttonSelect.setOnClickListener {
            filePaths.clear()
            FilePickerBuilder.instance
                    .setMaxCount(5) //optional
                    .setSelectedFiles(filePaths) //optional
                    .setActivityTheme(R.style.LibAppTheme) //optional
                    .pickPhoto(this)
        }

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            galleryAdapter.removeItem(position)
            Toast.makeText(applicationContext, "Removed item at : $position", Toast.LENGTH_LONG).show()
        }

        buttonAlert.setOnClickListener {
            val multipleImagesDialog = MultipleImagesDialog(filePaths).newInstance()
            multipleImagesDialog.isCancelable = false
            multipleImagesDialog.show(supportFragmentManager, "dialog grid view")
        }

        buttonUpload.setOnClickListener { uploadToFirebase(filePaths) }

    }

    private fun uploadToFirebase(filePaths: ArrayList<Uri>){
        Toast.makeText(applicationContext,filePaths.size.toString(),Toast.LENGTH_SHORT).show()
        var img=0
        for (i in filePaths) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, i)
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
                        val url=downloadUri.toString()
                        Log.d("URL"+img++,downloadUri.toString())
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
        when (requestCode) {
            FilePickerConst.REQUEST_CODE_PHOTO -> {
                val dataList = data!!.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)

                if (resultCode == RESULT_OK && data != null) {
                    Log.d("DATALIST: ", data.toString())
                    for (path in dataList) {
                        Log.d("DATAPATH: ", listOf(path).toString())
                        filePaths.addAll(listOf(path))
                    }
                    galleryAdapter = GalleryAdapter(applicationContext, filePaths)
                    gridView.verticalSpacing = gridView.horizontalSpacing
                    val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
                    gridView.adapter = galleryAdapter
                }


            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
