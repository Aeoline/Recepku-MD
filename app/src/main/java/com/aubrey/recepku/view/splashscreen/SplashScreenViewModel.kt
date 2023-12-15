package com.aubrey.recepku.view.splashscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.data.userpref.UserPreferences
import com.aubrey.recepku.data.userpref.dataStore

class SplashScreenViewModel (private val userPreferences: UserPreferences) : ViewModel() {

    suspend fun getCookie():LiveData<ProfileModel>{
        return userPreferences.getCookie().asLiveData()
    }


    companion object {
        @Volatile
        private var instance: SplashScreenViewModel? = null

        fun getInstance(application: Application): SplashScreenViewModel {
            return instance ?: synchronized(this) {
                val userPreferences = UserPreferences.getInstance(application.dataStore)
                SplashScreenViewModel(userPreferences).also { instance = it }
            }
        }
    }
}