package com.mix333.catch2048.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mix333.catch2048.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val mProgress = findViewById<View>(R.id.splash_screen_progress_bar) as ProgressBar

        lifecycleScope.launch {
            var progress = 0
            while (progress < 100) {
                try {
                    delay(30)
                    mProgress.progress = progress
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                progress += 2
            }
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
