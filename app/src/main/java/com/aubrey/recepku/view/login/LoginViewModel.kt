package com.aubrey.recepku.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.data.userpref.UserPreferences

class LoginViewModel(private val repository: UserRepository, private val userPreferences: UserPreferences): ViewModel() {

    fun login(username: String,password: String) = repository.login(username, password)

    fun saveUser(): LiveData<ProfileModel>{
        return userPreferences.getCookie().asLiveData()
    }
}