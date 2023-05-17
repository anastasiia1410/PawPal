package com.example.pawpal.screens.authorization.logIn

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class LogInViewModel(application: Application) : BaseViewModel(application) {
    private val networkManager: NetworkManager

    val sendingLD = MutableLiveData<Boolean>()
    val hasPetsLD = MutableLiveData<Boolean>()
    val noInternetErrorLD = MutableLiveData<Throwable>()
    val timeOutErrorLD = MutableLiveData<Throwable>()

    init {
        networkManager = App.getInstance(application.applicationContext).manager
    }

    fun logIn(username: String, password: String) {
        val disposable = networkManager.logIn(username, password)
            .concatMap { networkManager.getPaw() }
            .delay(2, TimeUnit.SECONDS)
            .doOnSubscribe { sendingLD.postValue(true) }
            .doFinally { sendingLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listPaws -> hasPetsLD.value = listPaws.isNotEmpty() },
                { throwable ->
                    if (throwable is SocketTimeoutException) {
                        timeOutErrorLD.value = throwable
                    } else if (throwable is UnknownHostException) {
                        noInternetErrorLD.value = throwable
                    }
                })

        compositeDisposable.add(disposable)
    }
}