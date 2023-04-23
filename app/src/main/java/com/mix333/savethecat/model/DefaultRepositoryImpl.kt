package com.mix333.savethecat.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mix333.savethecat.model.database.FirebaseRealtimeDatabase

class DefaultRepositoryImpl(private val database: FirebaseRealtimeDatabase) : DefaultRepository {

    val remoteConfig : FirebaseRemoteConfig

    private val _displayConfig = MutableLiveData(false)
    val displayConfig : LiveData<Boolean> = _displayConfig

    init {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }
    override fun getDatabase() = database.getDatabase()
    override fun checkConfigFlag() {
        TODO("Not yet implemented")
    }

    private val reference = database.getDatabase().getReference("url")

    fun getMyUrl() = reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = dataSnapshot.getValue<String>()
            Log.d("DefaultRepository", "Value is: $value")
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w("DefaultRepository", "Failed to read value.", error.toException())
        }
    })

}