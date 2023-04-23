package com.mix333.savethecat.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mix333.savethecat.R


class WebViewActivity : AppCompatActivity() {

    private val webView : WebView by lazy {findViewById(R.id.web_view)}
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.settings.javaScriptEnabled = true
        val databaseRef = Firebase.database.reference.child("url")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val url = dataSnapshot.getValue(String::class.java)
                if (url != null) {
                    // Load the URL in the WebView
                    webView.loadUrl(url)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
//        val pinterestUrl = "https://www.pinterest.com/search/pins/?q=adnroid&rs=typed"
//        webView.loadUrl(pinterestUrl)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()}
//                } else {
//                    isEnabled = false
//                    onBackPressedDispatcher.onBackPressed()
//                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

}