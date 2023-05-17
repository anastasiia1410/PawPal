package com.example.pawpal.screens.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.core.preference.AppPreference
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val appPreference: AppPreference
    val exitLD = MutableLiveData(false)
    val navigationLD = MutableLiveData(false)
    val detailNavigateLD = MutableLiveData<Pair<Boolean, Long>>()

    init {
        appPreference = App.getInstance(application.applicationContext).appPreferences
    }

    fun exit() {
        val disposable = Completable.create { emitter ->
            appPreference.clear()
            if (!emitter.isDisposed) emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                exitLD.postValue(true)
            }
        compositeDisposable.add(disposable)
    }

    fun navigateToCreateReminder() {
        navigationLD.value = true
    }

    fun navigateToDetailReminder(id: Long?) {
        detailNavigateLD.value = Pair(true, id!!)
    }
}