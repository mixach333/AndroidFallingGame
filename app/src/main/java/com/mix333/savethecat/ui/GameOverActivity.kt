package com.mix333.savethecat.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.mix333.savethecat.R


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
        val score = requireNotNull(intent.extras?.getInt("points"))
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
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                restart()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun restart() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}