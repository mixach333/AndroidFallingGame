package com.mix333.savethecat.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    fun startWebView(context: Context) {
        viewModelScope.launch {
            delay(10000)
            val intent = Intent(context, WebViewActivity::class.java)
            context.startActivity(intent)
        }
    }

}