package com.aubrey.recepku.view.register

import androidx.lifecycle.ViewModel
import com.aubrey.recepku.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository): ViewModel() {

    fun register(username: String,password: String, email: String) =
        repository.daftar(username, password, email)
}