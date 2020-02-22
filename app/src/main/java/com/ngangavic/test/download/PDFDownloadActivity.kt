package com.ngangavic.test.download

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ngangavic.test.R

class PDFDownloadActivity : AppCompatActivity() {

    lateinit var editTextUrl: EditText
    lateinit var buttonDownload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfdownload)

        editTextUrl = findViewById(R.id.editTextUrl)
        buttonDownload = findViewById(R.id.buttonDownload)

        requestPermissions()

        buttonDownload.setOnClickListener {
            if (TextUtils.isEmpty(editTextUrl.text.toString())) {
                editTextUrl.requestFocus()
                editTextUrl.error = "Cannot be empty"
            } else {
                DownloadTask(this, editTextUrl.text.toString())
            }
        }

    }

    private fun requestPermissions(): Boolean {
        val writePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded = ArrayList<String>()

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(),
                    200
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            200 -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                        PackageManager.PERMISSION_GRANTED

                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {

                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                        this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                        ) {
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

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    startActivity(
                            Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:com.ngangavictor.testapp")
                            )
                    )
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }


}
