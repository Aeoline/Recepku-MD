package com.aubrey.recepku.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: UserRepository): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean>{
        return repository.getThemeSetting()
    }
    fun saveThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkMode)
        }
    }
}