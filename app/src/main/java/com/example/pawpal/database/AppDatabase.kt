package com.example.pawpal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pawpal.database.AppDatabase.Companion.DATABASE_VERSION
import com.example.pawpal.database.dao.ReminderDao
import com.example.pawpal.database.entity.ReminderDatabase

@Database(entities = [ReminderDatabase::class], version = DATABASE_VERSION)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        const val DATABASE_VERSION = 1
    }
}