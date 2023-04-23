package com.mix333.savethecat.model.database

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRealtimeDatabase {
    fun getDatabase() = Firebase.database
    fun getReference() = getDatabase().reference
}