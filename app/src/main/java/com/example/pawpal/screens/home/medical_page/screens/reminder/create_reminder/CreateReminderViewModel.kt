package com.example.pawpal.screens.home.medical_page.screens.reminder.create_reminder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.database.DatabaseManager
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import com.example.pawpal.util.getFullDate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date

class CreateReminderViewModel(application: Application) : BaseViewModel(application) {
    val saveReminderLD = MutableLiveData<Reminder>()
    private val titleLD: MutableLiveData<String> = MutableLiveData()
    private val notifyLD: MutableLiveData<Int> = MutableLiveData()
    private val periodLD: MutableLiveData<Int> = MutableLiveData()
    private val timeLD: MutableLiveData<String> = MutableLiveData()
    private val dateLD: MutableLiveData<String> = MutableLiveData()
    private val reminderLD = MediatorLiveData<Reminder>().apply {
        val listener: Observer<Any> = Observer<Any> {
            val fullDate = try {
                (dateLD.value + " " + timeLD.value).getFullDate()
            } catch (e: Exception) {
                Date()
            }
            value = Reminder(
                title = titleLD.value ?: "",
                notify = notifyLD.value ?: 0,
                date = fullDate,
                period = periodLD.value ?: 0
            )
        }
        addSource(titleLD, listener)
        addSource(notifyLD, listener)
        addSource(periodLD, listener)
        addSource(timeLD, listener)
        addSource(dateLD, listener)
    }
    private val database: DatabaseManager

    val isValidInput: LiveData<Boolean> = Transformations.map(reminderLD) {
        return@map (it.title.isNotEmpty()
                && it.notify != 0)
//                && it.date.after(Date()))
    }


    init {
        database = App.getInstance(application.applicationContext).database
    }


    fun insertData() {
        val disposable = database.insert(reminderLD.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { saveReminderLD.postValue(reminderLD.value) }
        compositeDisposable.add(disposable)
    }

    fun changeTitle(newTitle: String) {
        if (titleLD.value != newTitle) {
            titleLD.postValue(newTitle)
        }
    }

    fun changeNotify(newNotify: String) {
        val intNotify = newNotify.toInt()
        if (notifyLD.value != intNotify) {
            notifyLD.postValue(intNotify)
        }
    }

    fun changeTime(newTime: String) {
        if (timeLD.value != newTime) {
            timeLD.postValue(newTime)
        }
    }

    fun changeDate(newDate: String) {
        if (dateLD.value != newDate) {
            dateLD.postValue(newDate)
        }
    }

    fun changePeriod(newPeriod: String) {
        val intPeriod = newPeriod.toInt()
        if (periodLD.value != intPeriod) {
            periodLD.postValue(intPeriod)
        }
    }
}