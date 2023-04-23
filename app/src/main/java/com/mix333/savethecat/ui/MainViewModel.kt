package com.mix333.savethecat.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mix333.savethecat.model.DefaultRepository
import com.mix333.savethecat.model.DefaultRepositoryImpl
import com.mix333.savethecat.model.database.FirebaseRealtimeDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val repository = DefaultRepositoryImpl(FirebaseRealtimeDatabase())
    fun getRemoteConfig() = repository.remoteConfig

    fun startWebView(context: Context) {
        viewModelScope.launch {
            delay(5000)
            val intent = Intent(context, WebViewActivity::class.java)
            context.startActivity(intent)
        }
    }

}