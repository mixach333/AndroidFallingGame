package com.mix333.savethecat.ui

import androidx.lifecycle.ViewModel
import com.mix333.savethecat.model.DefaultRepositoryImpl


class MainViewModel : ViewModel() {
    private val repository = DefaultRepositoryImpl()
    fun getRemoteConfig() = repository.getRemoteConfig()
}