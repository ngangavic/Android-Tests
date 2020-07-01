package com.ngangavic.test.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class ChatActivity : AppCompatActivity() {

    private lateinit var textViewTitle:TextView
    private lateinit var editTextMessage:EditText
    private lateinit var imageButtonSend:Button
    private lateinit var recyclerviewMessages:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        textViewTitle=findViewById(R.id.textViewTitle)
        editTextMessage=findViewById(R.id.editTextMessage)
        imageButtonSend=findViewById(R.id.imageButtonSend)
        recyclerviewMessages=findViewById(R.id.recyclerviewMessages)
    }
}