package com.ngangavic.test.recorder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ngangavic.test.MainActivity
import com.ngangavic.test.R

class RecorderActivity : AppCompatActivity() {
    lateinit var buttonStart: Button
    lateinit var buttonPause: Button
    lateinit var buttonStop: Button
    lateinit var layoutRecord: ConstraintLayout

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)
        buttonStart = findViewById(R.id.buttonStart)
        buttonStop = findViewById(R.id.buttonStop)
        buttonPause = findViewById(R.id.buttonPause)
        layoutRecord = findViewById(R.id.layoutRecord)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            buttonPause.visibility = View.VISIBLE
        } else {
            buttonPause.visibility = View.GONE
        }

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, 0)
        } else {
            output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
            Log.d("PATH", output!!)
            mediaRecorder = MediaRecorder()

            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output)
        }

        buttonStart.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions, 0)
            } else {
                startRecording()
            }
        }

        buttonPause.setOnClickListener {
            pauseRecording()
        }

        buttonStop.setOnClickListener {
            stopRecording()
        }

    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            Snackbar.make(layoutRecord, "Recording stopped!", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(layoutRecord, "You are not recording right now!", Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun pauseRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (state) {
                if (!recordingStopped) {
                    Snackbar.make(layoutRecord, "Stopped!", Snackbar.LENGTH_SHORT).show()
                    mediaRecorder?.pause()
                    recordingStopped = true
                    buttonPause.text = "Resume"
                } else {
                    resumeRecording()
                }
            }
        }
    }

    private fun resumeRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Snackbar.make(layoutRecord, "Resume!", Snackbar.LENGTH_SHORT).show()
            mediaRecorder?.resume()
            buttonPause.text = "Pause"
            recordingStopped = false
        }
    }

    private fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Snackbar.make(layoutRecord, "Recording started", Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(layoutRecord, "Recording failed", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }
}
