package com.ngangavic.test.timeticker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ngangavic.test.R
import java.util.*

class TimeTickerActivity : AppCompatActivity() {

    private lateinit var textViewDisplay:TextView
    private lateinit var timeTickerReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_ticker)

        textViewDisplay=findViewById(R.id.textViewDisplay)

        textViewDisplay.text=Calendar.getInstance().get(Calendar.SECOND).toString()

        timeTickerReceiver=object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.getAction()?.compareTo(Intent.ACTION_TIME_TICK)==0) {
                    textViewDisplay.text=Calendar.getInstance().get(Calendar.SECOND).toString()
                }
                }
            }

        registerReceiver(timeTickerReceiver, IntentFilter(Intent.ACTION_TIME_TICK))

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(timeTickerReceiver)
    }
}