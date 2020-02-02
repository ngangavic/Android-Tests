package com.ngangavic.test.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ngangavic.test.R

class WebViewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var queue: RequestQueue

    companion object {
        var ShowOrHideWebViewInitialUse = "show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        queue = Volley.newRequestQueue(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                ShowOrHideWebViewInitialUse = "hide"
                progressBar.setVisibility(View.GONE)
                webView.visibility = View.VISIBLE
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.setProgress(newProgress)
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE)
                }
            }
        }

        sendRequest()
//        webView.loadData("<iframe src=\"<?php echo $iframe_src;?>\" width=\"100%\" height=\"720px\" scrolling=\"auto\" frameBorder=\"0\"> <p>Unable to load the payment page</p> </iframe>\n")


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun sendRequest() {
        val str = object : StringRequest(Method.POST, "http://192.168.0.101/www.android.com/pesapal/action.php",
                Response.Listener { response ->
                    Log.d("MAINACTIVITY:", response.toString())
//                    webView.loadData(response, "text/html", "UTF-8")
                    webView.loadData("<iframe src=" + response + " width=\"100%\" height=\"720px\" scrolling=\"auto\" frameBorder=\"0\"> <p>Unable to load the payment page</p> </iframe>\n", "text/html", "UTF-8")
//                    webView.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + response, "text/html", "UTF-8", null)


                },
                Response.ErrorListener { error ->
                    error.printStackTrace()

                }) {

        }
        str.retryPolicy = DefaultRetryPolicy(
                7000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(str)
    }


}
