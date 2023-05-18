package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.database.DatabaseManager
import com.example.pawpal.screens.home.medical_page.entity.Note
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date

class DetailReminderViewModel(application: Application) : BaseViewModel(application) {
    val reminderWithNotesLD = MutableLiveData<Pair<Reminder, List<Note>>>()
    private val database: DatabaseManager

    init {
        database = App.getInstance(application.applicationContext).database
    }

    fun insertData(reminderId: Long, title: String) {
        val note = Note(
            reminderId = reminderId,
            title = title,
            date = Date()
        )
        val disposable = database.insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun loadReminderWithNotes(id: Long) {
        val disposable = database.getReminderById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (reminder, notesList) ->
               reminderWithNotesLD.postValue(reminder to notesList)
            }
        compositeDisposable.add(disposable)
    }
}