package com.mix333.savethecat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mix333.savethecat.domain.DefaultRepository


class MainViewModel(private val repository: DefaultRepository) : ViewModel() {
    fun getRemoteConfig() = repository.getRemoteConfig()

}
class MainViewModelFactory(private val repository: DefaultRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        else throw IllegalStateException("Unknown VM")
    }
}
