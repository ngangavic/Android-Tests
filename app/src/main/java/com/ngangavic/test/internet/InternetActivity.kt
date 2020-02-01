package com.ngangavic.test.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ngangavic.test.R
import kotlinx.android.synthetic.main.activity_internet.*

class InternetActivity : AppCompatActivity() {
    lateinit var buttonConnection:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet)
        buttonConnection=findViewById(R.id.buttonConnection)

        buttonConnection.setOnClickListener {

            val ConnectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = ConnectionManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected == true) {
                Snackbar.make(root, "Connected to Internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            } else {
                Snackbar.make(root, "Not connected to Internet", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }


    }
}
