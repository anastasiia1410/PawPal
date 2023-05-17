package com.example.pawpal.core

import android.app.Application
import android.content.Context
import com.example.pawpal.core.preference.AppPreference
import com.example.pawpal.data.network.NetworkManager
import com.example.pawpal.database.DatabaseManager

class App : Application() {
    lateinit var manager: NetworkManager
        private set
    lateinit var appPreferences: AppPreference
        private set
    lateinit var database: DatabaseManager
        private set
    override fun onCreate() {
        super.onCreate()
        appPreferences = AppPreference(this)
        manager = NetworkManager(this)
        database = DatabaseManager(this)
    }

    companion object {
        fun getInstance(context: Context): App = context.applicationContext as App
    }
}