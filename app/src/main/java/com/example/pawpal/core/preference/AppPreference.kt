package com.example.pawpal.core.preference

import android.content.Context
import androidx.core.content.edit

class AppPreference(context: Context) {
    private val sharedPreference =
        context.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE)

    val token: String?
        get() = sharedPreference.getString(TOKEN_KEY, null)

    val userId: String?
        get() = sharedPreference.getString(USER_KEY, null)

    fun saveToken(token: String?) {
        sharedPreference.edit{
            putString(TOKEN_KEY, token)
        }
    }

    fun saveUserId(userId: String?) {
        sharedPreference.edit{
            putString(USER_KEY, userId)
        }
    }

    fun clear(){
        sharedPreference.edit { clear() }
    }

    companion object {
        private const val APP_PREFERENCE_NAME = "app.preferences"
        private const val TOKEN_KEY = "token_key"
        private const val USER_KEY = "user_key"
    }
}

