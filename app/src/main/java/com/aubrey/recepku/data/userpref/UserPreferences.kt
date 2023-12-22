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
        val UID_KEY = stringPreferencesKey("uid")
        val EMAIL_KEY = stringPreferencesKey("email")
        val TOKEN_KEY = stringPreferencesKey("token")

        private var INSTANCE: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserPreferences(dataStore)
            }.also { INSTANCE = it }
    }

    suspend fun deleteCookies(){
        dataStore.edit {preference->
            preference.remove(TOKEN_KEY)
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
            it[TOKEN_KEY] = user.token
        }
        Log.d("Token Tersimpan","$user")
    }

    fun getCookie(): Flow<ProfileModel> {
        return dataStore.data.map { preference ->
            ProfileModel(
                preference[UID_KEY]?:"",
                preference[NAME_KEY]?:"",
                preference[EMAIL_KEY]?:"",
                preference[TOKEN_KEY] ?: ""
            )
        }
    }

}