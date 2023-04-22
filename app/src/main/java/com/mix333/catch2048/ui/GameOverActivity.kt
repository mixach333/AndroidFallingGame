package com.mix333.catch2048.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mix333.catch2048.R

class GameOverActivity : AppCompatActivity() {


    private lateinit var tvScore: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var btnRestart: ImageView
    private lateinit var ivWinningCup: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        tvScore = findViewById(R.id.tv_score)
        tvHighScore = findViewById(R.id.tv_high_score)
        btnRestart = findViewById(R.id.btn_restart_game)
        ivWinningCup = findViewById(R.id.iv_winning_cup)
        //val score = intent.getIntExtra("score", 0)
        val score = requireNotNull(intent.extras?.getInt("points"))
        Toast.makeText(this, "score is: $score", Toast.LENGTH_LONG).show()
        tvScore.text = "The game is ended, your score is $score"
        val sharedPreferences = getSharedPreferences("score_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)
        if (score > highest) {
            highest = score
            sharedPreferences.edit().putInt("highest", highest).apply()
        } else ivWinningCup.visibility = View.INVISIBLE
        tvHighScore.text = "The highest score is: $highest"
        btnRestart.setOnClickListener {
            restart()
        }

    }

    private fun restart() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        restart()
    }

}