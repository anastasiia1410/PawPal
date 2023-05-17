package com.example.pawpal.screens.navigate

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.core.preference.AppPreference

class NavigateViewModel(application: Application) : BaseViewModel(application) {
    private val appPreference: AppPreference
    val screenLD = MutableLiveData<StartScreen>()

    init {
        appPreference = App.getInstance(application.applicationContext).appPreferences

        if (appPreference.token == null) {
            screenLD.value = StartScreen.LogIn
        } else {
            screenLD.value = StartScreen.Home
        }
    }
}