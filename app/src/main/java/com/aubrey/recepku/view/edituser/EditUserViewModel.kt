package com.aubrey.recepku.view.edituser

import androidx.lifecycle.ViewModel
import com.aubrey.recepku.data.repository.UserRepository

class EditUserViewModel(private val repository: UserRepository): ViewModel() {
    fun editUser(username: String,password: String) = repository.editUser(username, password)

    fun editPass(newPass: String,confirmPass: String,password: String) = repository.editPass(newPass, confirmPass, password)
}