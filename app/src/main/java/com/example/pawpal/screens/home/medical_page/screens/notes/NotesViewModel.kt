package com.example.pawpal.screens.home.medical_page.screens.notes

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.database.DatabaseManager
import com.example.pawpal.screens.home.medical_page.entity.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NotesViewModel(application: Application) : BaseViewModel(application) {
    val notesLd = MutableLiveData<List<Note>>()
    private val database: DatabaseManager

    init {
        database = App.getInstance(application.applicationContext).database
    }

    fun getAllNotes() {
        val disposable = database.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notesList ->
                notesLd.postValue(notesList)
            }
        compositeDisposable.add(disposable)
    }
}