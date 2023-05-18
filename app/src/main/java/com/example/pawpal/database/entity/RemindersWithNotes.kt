package com.example.pawpal.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RemindersWithNotes(
    @Embedded val reminderDatabase: ReminderDatabase,
    @Relation(
        parentColumn = "reminderId",
        entityColumn = "reminderId"
    )
    val listNotesDatabase: List<NotesDatabase>
)
