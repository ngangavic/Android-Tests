package com.ngangavic.test.webview

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.ngangavic.test.R

class WebViewActivity : AppCompatActivity() {
    lateinit var webView:WebView
    lateinit var progressBar:ProgressBar

    companion object{
        var ShowOrHideWebViewInitialUse = "show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView=findViewById(R.id.webView)
        progressBar=findViewById(R.id.progressBar)
        webView.settings.javaScriptEnabled=true
        webView.webViewClient=object:WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility=View.VISIBLE
                webView.visibility=View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                ShowOrHideWebViewInitialUse = "hide"
                progressBar.setVisibility(View.GONE)
                webView.visibility=View.VISIBLE
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient=object :WebChromeClient(){

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.setProgress(newProgress)
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE)
                }
            }
        }


        webView.loadUrl("https://www.pesapal.com")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}
