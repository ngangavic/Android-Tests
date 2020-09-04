package com.ngangavic.test.usehover

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.hover.sdk.permissions.PermissionActivity
import com.ngangavic.test.R


class UseHoverActivity : AppCompatActivity() {

    lateinit var buttonSafBundlesNo: Button
    lateinit var buttonPermissions: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_hover)
        Hover.initialize(this)
        buttonSafBundlesNo = findViewById(R.id.buttonSafBundlesNo)
        buttonPermissions = findViewById(R.id.buttonPermissions)

        buttonPermissions.setOnClickListener {
            val i = Intent(applicationContext, PermissionActivity::class.java)
            startActivityForResult(i, 0)
        }
        buttonSafBundlesNo.setOnClickListener {
            val intent = HoverParameters.Builder(this)
                    .request("action_id")
                    .extra("variable name", "value") // Only if your action has variables
                    .buildIntent()
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val sessionTextArr = data!!.getStringArrayExtra("session_messages")
            val uuid = data.getStringExtra("uuid")
            Log.d("UUID", uuid.toString())
            Log.d("MESSAGES", sessionTextArr.toString())
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data!!.getStringExtra("error"), Toast.LENGTH_LONG).show()
        }
    }
}
