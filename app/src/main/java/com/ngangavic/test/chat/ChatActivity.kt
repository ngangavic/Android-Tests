package com.ngangavic.test.chat

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ngangavic.test.R

class ChatActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var editTextMessage: EditText
    private lateinit var imageButtonSend: ImageButton
    private lateinit var recyclerviewMessages: RecyclerView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        textViewTitle = findViewById(R.id.textViewTitle)
        editTextMessage = findViewById(R.id.editTextMessage)
        imageButtonSend = findViewById(R.id.imageButtonSend)
        recyclerviewMessages = findViewById(R.id.recyclerviewMessages)

        auth = FirebaseAuth.getInstance()

    }

    private fun authRegisterAlert() {
        val alert = AlertDialog.Builder(this)
        alert.setCancelable(false)
        val customLayout = getLayoutInflater().inflate(R.layout.dialog_chat_register, null)
        alert.setView(customLayout)

        val email = customLayout.findViewById<EditText>(R.id.registerEmail)
        val password = customLayout.findViewById<EditText>(R.id.registerPassword)


        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alert.setPositiveButton("Register", DialogInterface.OnClickListener { dialog, which ->
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            editTextMessage.isEnabled=true
                            imageButtonSend.isEnabled=true
                            dialog.cancel()
                            Toast.makeText(baseContext, "Authentication success", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
        })
        val dialog = alert.create()
        customLayout.findViewById<TextView>(R.id.textViewLogin).setOnClickListener {
            dialog.cancel()
            authLoginAlert()
        }
        dialog.show()
    }

    private fun authLoginAlert() {
        val alert = AlertDialog.Builder(this)
        alert.setCancelable(false)
        val customLayout = getLayoutInflater().inflate(R.layout.dialog_chat_login, null)
        alert.setView(customLayout)

        val email = customLayout.findViewById<EditText>(R.id.loginEmail)
        val password = customLayout.findViewById<EditText>(R.id.loginPassword)

        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alert.setPositiveButton("Login", DialogInterface.OnClickListener { dialog, which ->
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            editTextMessage.isEnabled=true
                            imageButtonSend.isEnabled=true
                            dialog.cancel()
                            Toast.makeText(baseContext, "Authentication success", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
        })
        val dialog = alert.create()
        customLayout.findViewById<TextView>(R.id.textViewRegister).setOnClickListener {
            dialog.cancel()
            authRegisterAlert()
        }
        dialog.show()
    }

    public override fun onStart() {
        super.onStart()
        auth.signOut()
        val currentUser = auth.currentUser
        Log.e("CHAT USER", currentUser.toString())
        if (currentUser == null) {
            editTextMessage.isEnabled=false
            imageButtonSend.isEnabled=false
            authLoginAlert()
        }
    }
}