package com.mix333.savethecat.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mix333.savethecat.R
import com.mix333.savethecat.model.DefaultRepositoryImpl


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(DefaultRepositoryImpl())
    }
    private val remoteConfig by lazy { viewModel.getRemoteConfig() }
    private val startGame: ImageView by lazy { findViewById(R.id.start_game) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        startGame.setOnClickListener { startGame() }
        setupRemoteConfig()
        checkRemoteConfig()
    }

    private fun startGame() {
        val gameView = GameView(this)
        setContentView(gameView)
    }

    private fun checkRemoteConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { isNavigate ->
                if (isNavigate.isSuccessful) {
                    val flag = remoteConfig.getBoolean("isNavigate")
                    Log.d("RemoteConfigCheck", "$flag")
                    if (flag) {
                        startWebViewActivity()
                    }
                }
            }
    }

    private fun startWebViewActivity() {
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d("Update", "Updated keys: " + configUpdate.updatedKeys)

                if (configUpdate.updatedKeys.contains("isNavigate")) {
                    remoteConfig.activate().addOnCompleteListener {
                        checkRemoteConfig()
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("Error update", "Config update error with code: " + error.code, error)
            }
        })
    }
}