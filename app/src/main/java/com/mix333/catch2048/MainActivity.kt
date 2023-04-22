package com.mix333.catch2048


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        button = findViewById(R.id.start_game)
        button.setOnClickListener { startGame(it) }
    }

    fun startGame(view: View) {
        val gameView: GameView = GameView(this)
        setContentView(gameView)
        lifecycleScope.launch {
            while (true) {
                delay(100)
                //gameView.invalidate()
            }

        }
    }
}