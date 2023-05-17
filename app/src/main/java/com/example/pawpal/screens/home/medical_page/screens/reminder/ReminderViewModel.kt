package com.example.pawpal.screens.home.medical_page.screens.reminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.database.DatabaseManager
import com.example.pawpal.screens.home.medical_page.entity.ListItems
import com.example.pawpal.util.groupByListItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ReminderViewModel(application: Application) : BaseViewModel(application) {
    val itemsLD: MutableLiveData<List<ListItems>> = MutableLiveData()
    private val database: DatabaseManager

    init {
        database = App.getInstance(application.applicationContext).database
    }

    fun load() {
        val disposable = database.getAllReminders()
            .map { reminderList -> groupByListItem(getApplication(), reminderList) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listItems -> itemsLD.postValue(listItems) }

        compositeDisposable.add(disposable)
    }
}