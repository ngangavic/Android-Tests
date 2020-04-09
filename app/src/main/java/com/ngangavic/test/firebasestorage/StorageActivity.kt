package com.ngangavic.test.firebasestorage

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.ngangavic.test.firebasestorage.Credetials.Companion.email
import com.ngangavic.test.firebasestorage.Credetials.Companion.password
import java.io.ByteArrayOutputStream
import java.io.IOException

class StorageActivity : AppCompatActivity() {

    lateinit var storage: FirebaseStorage //android-test
    lateinit var imageView: ImageView
    lateinit var buttonUpload: Button
    lateinit var buttonViewImages: Button
    lateinit var buttonPickImage: Button
    lateinit var buttonCamera: Button
    private lateinit var auth: FirebaseAuth
    lateinit var editTextName: EditText
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        imageView = findViewById(R.id.imageView)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonViewImages = findViewById(R.id.buttonViewImages)
        buttonPickImage = findViewById(R.id.buttonPickImage)
        buttonCamera = findViewById(R.id.buttonCamera)
        editTextName = findViewById(R.id.editTextName)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "SignIn success", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "SignIn failed", Toast.LENGTH_LONG).show()
                    }
                }

        buttonPickImage.setOnClickListener { chooseImage() }

        buttonUpload.setOnClickListener { uploadImage() }

        buttonCamera.setOnClickListener { useCamera() }

        buttonViewImages.setOnClickListener { startActivity(Intent(applicationContext, ViewActivity::class.java)) }

        requestPermissions()
    }

    private fun chooseImage() {
        val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
    }

    private fun useCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(applicationContext.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 201)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                imageView.setImageBitmap(bitmap);
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == 201 && resultCode == RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadImage() {
        if (TextUtils.isEmpty(editTextName.text.toString())) {
            editTextName.requestFocus()
            editTextName.error = "Required"
        } else {
            val storageRef = storage.reference.child("android-test/" + editTextName.text.toString())
            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

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
                        database.child("android-test").child(editTextName.text.toString().replace(" ", "")).child("name").setValue(editTextName.text.toString())
                        database.child("android-test").child(editTextName.text.toString().replace(" ", "")).child("url").setValue(downloadUri.toString())
                        imageView.setImageResource(R.drawable.ic_image)
                        editTextName.text.clear()
                        Toast.makeText(applicationContext, "Success!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
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

        val listPermissionsNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
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
