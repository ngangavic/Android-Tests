package com.ngangavic.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BinaryActivity : AppCompatActivity() {
    lateinit var buttonEncode: Button
    lateinit var buttonDecode: Button
    lateinit var editText: EditText
    lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary)
        editText = findViewById(R.id.editText)
        buttonEncode = findViewById(R.id.buttonEncode)
        buttonDecode = findViewById(R.id.buttonDecode)
        textViewResult = findViewById(R.id.textViewResult)

        buttonEncode.setOnClickListener {
            encode()
        }

        buttonDecode.setOnClickListener {
            decode()
        }
    }

    private fun encode() {
        val txt = editText.text.toString()
        val binary = strToBinary(txt)
        textViewResult.text = binary
        //hideKeyboard()
    }

    private fun decode() {
        val binary = editText.text.toString()
        val txt = binaryToString(binary)
        textViewResult.text = txt
        //hideKeyboard()
    }

    fun strToBinary(str: String): String {
        val builder = StringBuilder()

        for (c in str.toCharArray()) {
            val toString = Integer.toString(c.toInt(), 2) // get char value in binary
            builder.append(String.format("%08d", Integer.parseInt(toString))) // we complete to have 8 digits
        }

        return builder.toString()
    }

    fun binaryToString(binary: String): String {
        if (!isBinary(binary))
            return "Not a binary value"

        val chars = CharArray(binary.length / 8)
        var i = 0

        while (i < binary.length) {
            val str = binary.substring(i, i + 8)
            val nb = Integer.parseInt(str, 2)
            chars[i / 8] = nb.toChar()
            i += 8
        }

        return String(chars)
    }

    fun isBinary(txt: String?): Boolean {
        if (txt != null && txt.length % 8 == 0) {
            for (c in txt.toCharArray()) {
                if (c != '0' && c != '1')
                    return false
            }

            return true
        }

        return false
    }
}
