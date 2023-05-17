package com.example.pawpal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pawpal.database.entity.ReminderDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ReminderDao {
    @Insert
    fun insert(reminderDatabase: ReminderDatabase): Completable

    @Query("SELECT * FROM Reminder ORDER BY date ASC")
    fun getAllReminders(): Single<List<ReminderDatabase>>

    @Query("SELECT * FROM Reminder WHERE id IN(:idReminder)")
    fun getReminderById(idReminder: Long): Single<ReminderDatabase>
}