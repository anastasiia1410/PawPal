package com.example.pawpal.screens.home.profile_page

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.data.network.NetworkManager
import com.example.pawpal.entity.Paw
import com.example.pawpal.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    private val networkManager: NetworkManager

    val dataLD = MutableLiveData<Pair<User, Paw>>()
    val sendingLD = MutableLiveData<Boolean>()

    init {
        networkManager = App.getInstance(application.applicationContext).manager
        loadUserData()
    }

   private fun loadUserData() {
        val disposable =
            Single.zip(networkManager.getCurrentUser(), networkManager.getPaw()) { user, pawList ->
                user to pawList
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { (user, pawList) ->
                    dataLD.postValue(
                        Pair(
                            user,
                            pawList[0]
                        )
                    )
                }
        compositeDisposable.add(disposable)
    }

    fun uploadAvatar(uri: Uri) {
        val paw = dataLD.value?.second
        val disposable =
            networkManager.uploadImage(uri)
                .delay(1, TimeUnit.SECONDS)
                .concatMapCompletable { url ->
                    networkManager.updateAvatar(url)
                }
                .andThen(networkManager.getCurrentUser())
                .doOnSubscribe { sendingLD.postValue(true) }
                .doFinally { sendingLD.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ user ->
                    dataLD.postValue(
                        Pair(
                            user,
                            paw!!
                        )
                    )
                }
        compositeDisposable.add(disposable)
    }

    fun deleteAvatar() {
        val paw = dataLD.value?.second
        val disposable = networkManager.updateAvatar(null)
            .andThen(networkManager.getCurrentUser())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                dataLD.postValue(
                    Pair(
                        user,
                        paw!!
                    )
                )
            }
        compositeDisposable.add(disposable)
    }
}