package com.example.pawpal.screens.onboarding

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class OnboardingViewModel(application: Application) : BaseViewModel(application) {
    private val networkManager: NetworkManager
    val isAddSuccessful = MutableLiveData<Boolean>()

    init {
        networkManager = App.getInstance(application.applicationContext).manager
    }

    fun createPaw(name: String, age: Int) {
        val disposable = networkManager.addPaw(name, age)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isAddSuccessful.postValue(true) }

        compositeDisposable.add(disposable)
    }
}