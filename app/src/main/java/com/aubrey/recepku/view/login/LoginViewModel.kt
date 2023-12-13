package com.aubrey.recepku.view.login

import androidx.lifecycle.ViewModel
import com.aubrey.recepku.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun login(username: String,password: String) = repository.login(username, password)
}