package com.mix333.savethecat.domain

import com.google.firebase.remoteconfig.FirebaseRemoteConfig


interface DefaultRepository {
    fun getRemoteConfig() : FirebaseRemoteConfig
}