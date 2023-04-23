package com.mix333.savethecat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mix333.savethecat.R
import com.mix333.savethecat.model.DefaultRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
                        startWebViewActivity(this)
                    }
                }
            }
    }

    private fun startWebViewActivity(context: Context) {
        if (viewModel.checkInternetConnection(context)) {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                this,
                "Missing internet connection",
                Toast.LENGTH_SHORT
            ).show()
            lifecycleScope.launch {
                repeat(5) {
                    delay(360000)
                    startWebViewActivity(this@MainActivity)
                }
            }
        }
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