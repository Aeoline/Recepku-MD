package com.aubrey.recepku.data.userpref

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name= "login")
class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
 
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    companion object{
        val NAME_KEY = stringPreferencesKey("name")
        private val USER_KEY = stringPreferencesKey("userId")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN = booleanPreferencesKey("isLogin")

        val UID_KEY = stringPreferencesKey("uid")
        val EMAIL_KEY = stringPreferencesKey("email")
        private val COOKIE = stringPreferencesKey("cookie")

        private var INSTANCE: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserPreferences(dataStore)
            }.also { INSTANCE = it }
    }

    suspend fun saveSession(user: UserModel){
        dataStore.edit {
            it[NAME_KEY] = user.name
            it[USER_KEY] = user.userId
            it[TOKEN_KEY] = user.token
            it[IS_LOGIN] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[TOKEN_KEY].toString(),
                it[NAME_KEY].toString(),
                it[USER_KEY].toString(),
                it[IS_LOGIN]?: false
            )
        }
    }
    suspend fun logout(){
        dataStore.edit {
            it[TOKEN_KEY] = ""
            it[NAME_KEY] = ""
            it[USER_KEY] = ""
            it[IS_LOGIN] = false
            it[UID_KEY] = ""
            it[EMAIL_KEY] = ""
        }
    }

    suspend fun deleteCookies(){
        dataStore.edit {preference->
            preference.remove(COOKIE)
            preference.remove(UID_KEY)
            preference.remove(EMAIL_KEY)
            preference.remove(NAME_KEY)
        }
    }
    
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    suspend fun saveUser(user: ProfileModel){
        dataStore.edit {
            it[UID_KEY] = user.uid
            it[NAME_KEY] = user.username
            it[EMAIL_KEY] = user.email
            it[COOKIE] = user.cookie.toString()
        }
        Log.d("Token Tersimpan","$user")
    }

    fun getCookie(): Flow<ProfileModel> {
        return dataStore.data.map { preference ->
            ProfileModel(
                preference[UID_KEY]?:"",
                preference[NAME_KEY]?:"",
                preference[EMAIL_KEY]?:"",
                preference[COOKIE] ?: "",
            )
        }
    }

}