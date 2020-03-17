package com.ngangavic.test.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ngangavic.test.R
import org.json.JSONObject
import java.util.*

class MyService : Service() {

    lateinit var player: MediaPlayer
    lateinit var queue: RequestQueue

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        queue = Volley.newRequestQueue(this)
        Toast.makeText(this, "Service created!", Toast.LENGTH_SHORT).show()
        player = MediaPlayer.create(this, R.raw.laugh)
        player.isLooping = false
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show()
        sendRequest()
        player.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service stopped!", Toast.LENGTH_SHORT).show()
        player.stop()
        queue.cancelAll(this)
    }

    private fun sendRequest() {
        val str = object : StringRequest(
                Method.POST, "https://your url",
                Response.Listener { response ->
                    Log.d("SERVICE", response.toString())
                    val obj = JSONObject(response)


                },
                Response.ErrorListener { error ->

                    error.printStackTrace()

                }) {
            override fun getParams(): Map<String, String> {
                val param = HashMap<String, String>()
                param["uid"] = "0"
                return param
            }
        }
        str.retryPolicy = DefaultRetryPolicy(
                7000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }
}