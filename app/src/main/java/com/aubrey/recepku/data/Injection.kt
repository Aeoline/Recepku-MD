package com.aubrey.recepku.data

import android.content.Context
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.data.retrofit.ApiConfig
import com.aubrey.recepku.data.userpref.UserPreferences
import com.aubrey.recepku.data.userpref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): UserRepository{
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService,pref)
    }
}