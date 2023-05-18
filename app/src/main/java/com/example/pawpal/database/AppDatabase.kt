package com.example.pawpal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pawpal.database.dao.ReminderDao
import com.example.pawpal.database.entity.NotesDatabase
import com.example.pawpal.database.entity.ReminderDatabase

@Database(
    entities = [ReminderDatabase::class,
        NotesDatabase::class], version = 2
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}