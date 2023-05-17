package com.example.pawpal.database

import android.content.Context
import androidx.room.Room
import com.example.pawpal.database.entity.toReminder
import com.example.pawpal.database.entity.toReminderDatabase
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class DatabaseManager(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    fun insert(reminder: Reminder): Completable {
        return db.reminderDao()
            .insert(reminder.toReminderDatabase())
    }

    fun getAllReminders(): Single<List<Reminder>> {
        return db.reminderDao().getAllReminders()
            .map { reminders -> reminders.map { it.toReminder() } }
    }

    fun getReminderById(id: Long): Single<Reminder> {
        return db.reminderDao().getReminderById(id)
            .map { reminder -> reminder.toReminder() }
    }

    companion object {
        const val DATABASE_NAME = "pawPal.sqlite"

    }
}