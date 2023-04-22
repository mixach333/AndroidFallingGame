package com.mix333.catch2048


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var startGame: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        startGame = findViewById(R.id.start_game)
        startGame.setOnClickListener { startGame() }
    }

    fun startGame() {
        val gameView: GameView = GameView(this)
        setContentView(gameView)
    }
}