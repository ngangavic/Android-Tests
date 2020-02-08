package com.ngangavic.test.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.ngangavic.test.R

class MyService : Service() {

    lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_SHORT).show()
        player = MediaPlayer.create(this, R.raw.laugh)
        player.isLooping = false
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show()
        player.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service stopped!", Toast.LENGTH_SHORT).show()
        player.stop()
    }
}