package com.aubrey.recepku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.response.CheckUserResponse
import com.aubrey.recepku.data.response.DeleteResponse
import com.aubrey.recepku.data.response.EditPassResponse
import com.aubrey.recepku.data.response.EditUserResponse
import com.aubrey.recepku.data.response.ErrorResponse
import com.aubrey.recepku.data.response.LoginResponse
import com.aubrey.recepku.data.response.RefreshTokenResponse
import com.aubrey.recepku.data.response.RegisterResponse
import com.aubrey.recepku.data.retrofit.ApiConfig
import com.aubrey.recepku.data.retrofit.ApiService
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.data.userpref.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Cookie
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

class UserRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
): CoroutineScope {
    companion object {
        private var INSTANCE: UserRepository? = null

        fun clearInstance() {
            INSTANCE = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun daftar(username: String, password: String, email: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(username, password, email)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = if (errorJsonString != null) {
                try {
                    val jsonObject = JSONObject(errorJsonString)
                    jsonObject.getString("message")
                } catch (jsonException: Exception) {
                    "Error"
                }
            } else {
                "Error"
            }
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error"))
        }
    }



    fun login(username: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(username, password)
            val user = ProfileModel(
                uid = response.data?.uid ?: "",
                username = response.data?.username ?: "",
                email = response.data?.email ?: "",
                token = response.token ?: ""
            )
            userPreferences.saveUser(user)
            ApiConfig.token = response.token ?: ""
            emit(Result.Success(response))
            Log.d("Login", "Login Success: ${user.token}")
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = if (errorJsonString != null) {
                try {
                    val jsonObject = JSONObject(errorJsonString)
                    jsonObject.getString("message")
                } catch (jsonException: Exception) {
                    "Error"
                }
            } else {
                "Error"
            }
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error"))
        }
    }



    fun getThemeSetting(): LiveData<Boolean> {
        return userPreferences.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        return userPreferences.saveThemeSetting(isDarkMode)
    }

    fun editUser(username: String, password: String) : LiveData<Result<EditUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.editUser(username,password)
            val userResponse = apiService.login(username,password)
            val user = ProfileModel(
                uid = userResponse.data?.uid ?: "",
                username = userResponse.data?.username ?: "",
                email = userResponse.data?.email ?: "",
                token = userResponse.token ?: ""
            )
            checkUser()
            userPreferences.saveUser(user)
            if (user.token == ApiConfig.token) {
                emit(Result.Success(response))
                Log.d("EditUser", "EditUser: ${user.username}")
            } else {
                emit(Result.Error("Error"))
            }
        }catch (e: HttpException){
            emit(Result.Error(e.message ?: "Error"))
        }
    }

    fun editPass(password: String,newPassword: String, confirmPassword: String) : LiveData<Result<EditPassResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.editPass(password, newPassword, confirmPassword)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message?: "Error"))
        }
    }

    fun deleteUser(): LiveData<Result<DeleteResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.deleteUser()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message ?: "Error"))
        }
    }

    fun checkUser(): LiveData<Result<CheckUserResponse>> = liveData {
        val response = apiService.checkUser()
        if (response.error == true) {
            emit(Result.Error(response.message ?: "Error"))
            Log.d("CheckUser", "CheckUser: ${response.message}")
        } else {
            emit(Result.Success(response))
            Log.d("CheckUser", "CheckUser: ${response.message}")
        }
    }

    fun refreshToken(): LiveData<Result<RefreshTokenResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response: Response<RefreshTokenResponse> = apiService.refreshToken()
            Log.d("RefreshToken", "API response received: $response")

            if (response.isSuccessful) {
                val refreshTokenResponse: RefreshTokenResponse? = response.body()
                Log.d("RefreshToken", "API response body: $refreshTokenResponse")

                if (refreshTokenResponse != null) {
                    emit(Result.Success(refreshTokenResponse))
                    Log.d("RefreshToken", "RefreshToken: ${refreshTokenResponse.message}")
                } else {
                    emit(Result.Error("Error: Token is null"))
                }
            } else {
                emit(Result.Error("Error: ${response.message()}"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            Log.e("HttpException", "Error: $errorMessage", e)
            emit(Result.Error("Failed: $errorMessage"))
        } catch (e: Exception) {
            Log.e("Exception", "Error: ${e.message}", e)
            emit(Result.Error("Internet Issues: ${e.message}"))
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeoutException", "Timeout occurred", e)
            emit(Result.Error("Read timeout occurred"))
        } catch (e: IOException) {
            Log.e("IOException", "Network error occurred", e)
            emit(Result.Error("Network error occurred"))
        }
    }

}



