package com.aubrey.recepku.view.edituser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.data.response.ProfileResponse
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.data.userpref.UserPreferences

class EditUserViewModel(private val repository: UserRepository,private val userPreferences: UserPreferences): ViewModel() {
    fun editUser(username: String,password: String) = repository.editUser(username, password)

    fun editPass(newPass: String,confirmPass: String,password: String) = repository.editPass(newPass, confirmPass, password)

    fun saveUser(): LiveData<ProfileModel>{
        return userPreferences.getCookie().asLiveData()
    }
}