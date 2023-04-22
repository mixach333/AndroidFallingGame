package com.mix333.catch2048.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mix333.catch2048.R

class MainActivity : AppCompatActivity() {

    private lateinit var startGame: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        startGame = findViewById(R.id.start_game)
        startGame.setOnClickListener { startGame() }
    }

    private fun startGame() {
        val gameView = GameView(this)
        setContentView(gameView)
    }
}