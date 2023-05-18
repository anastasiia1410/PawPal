package com.example.pawpal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pawpal.screens.home.medical_page.entity.Note
import com.example.pawpal.util.getFullDate
import com.example.pawpal.util.getStringDate

@Entity(tableName = "Notes")
data class NotesDatabase(
    @PrimaryKey(true)
    val notesId: Long = 0L,
    val reminderId: Long,
    val title: String,
    val date: String,
)

fun NotesDatabase.toNote(): Note {
    return Note(
        notesId = this.notesId,
        reminderId = this.reminderId,
        title = this.title,
        date = (this.date).getFullDate(),
    )
}

fun Note.toNoteDatabase(): NotesDatabase {
    return NotesDatabase(
        notesId = this.notesId,
        reminderId = this.reminderId,
        title = this.title,
        date = (this.date).getStringDate(),
    )
}
