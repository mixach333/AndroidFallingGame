package com.mix333.savethecat.model


interface DefaultRepository {
    fun getDatabase() : Any
    fun checkConfigFlag()
}