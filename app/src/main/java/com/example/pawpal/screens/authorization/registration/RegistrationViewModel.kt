package com.example.pawpal.screens.authorization.registration

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RegistrationViewModel(application: Application) : BaseViewModel(application) {
    private val networkManager: NetworkManager

    private val tokenLD = MutableLiveData<String>()
    val sendingLD = MutableLiveData<Boolean>()
    val noInternetErrorLD = MutableLiveData<Throwable>()
    val timeOutErrorLD = MutableLiveData<Throwable>()

    init {
        networkManager = App.getInstance(application.applicationContext).manager
    }

    fun registration(username: String, password: String, email: String) {
        compositeDisposable.add(networkManager.registration(username, password, email)
            .doOnSubscribe { sendingLD.postValue(true) }
            .doFinally { sendingLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { token -> tokenLD.value = token },
                { throwable ->
                    if (throwable is SocketTimeoutException) {
                        timeOutErrorLD.value = throwable
                    } else if (throwable is UnknownHostException) {
                        noInternetErrorLD.value = throwable
                    }
                })
        )
    }
}