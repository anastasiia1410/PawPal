package com.example.pawpal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pawpal.database.entity.NotesDatabase
import com.example.pawpal.database.entity.ReminderDatabase
import com.example.pawpal.database.entity.RemindersWithNotes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ReminderDao {
    @Insert
    fun insert(reminderDatabase: ReminderDatabase): Completable

    @Insert
    fun insertNote(notesDatabase: NotesDatabase) : Completable

    @Query("SELECT * FROM Reminder ORDER BY date ASC")
    fun getAllReminders(): Single<List<ReminderDatabase>>

    @Transaction
    @Query("SELECT * FROM Reminder WHERE reminderId = :reminderId")
    fun getReminderByIdWithNotes(reminderId: Long): Single<RemindersWithNotes>
}