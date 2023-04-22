package com.mix333.catch2048

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOver : AppCompatActivity() {

//    private val tvScore : TextView by lazy {
//        findViewById(R.id.tv_score)
//    }
//    private val highScore : TextView by lazy {
//        findViewById(R.id.tv_score)
//    }

    private lateinit var tvScore : TextView
    private lateinit var tvHighScore : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        tvScore = findViewById(R.id.tv_score)
        tvHighScore = findViewById(R.id.tv_high_score)
        //val score = intent.getIntExtra("score", 0)
        val score = requireNotNull(intent.extras?.getInt("score"))
        tvScore.text = "The game is ended, your score is $score"
        val sharedPreferences = getSharedPreferences("score_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)
        //if(score>highest) sharedPreferences.edit().putInt("highest", score).apply()
        if(score>highest){
            highest = score
            sharedPreferences.edit().putInt("highest", highest).commit()
            tvHighScore.text = "The highest score is: $highest"
        }

    }

    fun restart(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}