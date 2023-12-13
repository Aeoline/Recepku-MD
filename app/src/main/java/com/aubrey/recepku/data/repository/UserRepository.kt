package com.aubrey.recepku.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.response.ErrorResponse
import com.aubrey.recepku.data.response.LoginResponse
import com.aubrey.recepku.data.response.RegisterResponse
import com.aubrey.recepku.data.retrofit.ApiService
import com.aubrey.recepku.data.userpref.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class UserRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
): CoroutineScope {
    companion object{
        private var INSTANCE: UserRepository? = null

        fun clearInstance(){
            INSTANCE = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): UserRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserRepository(apiService,userPreferences)
            }.also { INSTANCE = it }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun daftar(username: String, password: String, email: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(username, password, email)
            emit(Result.Success(response))
        }catch (e: HttpException) {
           emit(Result.Error(e.message?: "Error"))
        }
    }

    fun login(username: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.login(username,password)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message?: "Error"))
        }
    }

    fun getThemeSetting():LiveData<Boolean>{
        return userPreferences.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean){
        return userPreferences.saveThemeSetting(isDarkMode)
    }
}