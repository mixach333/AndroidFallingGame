package com.mix333.savethecat.model

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mix333.savethecat.domain.DefaultRepository

class DefaultRepositoryImpl : DefaultRepository {

    private val remoteConfig : FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    override fun getRemoteConfig(): FirebaseRemoteConfig = remoteConfig

}