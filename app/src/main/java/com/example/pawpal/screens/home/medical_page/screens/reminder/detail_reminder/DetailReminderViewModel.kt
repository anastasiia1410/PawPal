package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.database.DatabaseManager
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailReminderViewModel(application: Application) : BaseViewModel(application) {
    val reminderLD = MutableLiveData<Reminder>()
    private val database: DatabaseManager

    init {
        database = App.getInstance(application.applicationContext).database
    }

    fun loadReminder(id: Long) {
        val disposable = database.getReminderById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { reminder ->
                reminderLD.postValue(reminder)
            }
        compositeDisposable.add(disposable)
    }
}