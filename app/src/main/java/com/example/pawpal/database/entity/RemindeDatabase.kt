package com.example.pawpal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import com.example.pawpal.util.getFullDate
import com.example.pawpal.util.getStringDate

@Entity(tableName = "Reminder")
data class ReminderDatabase(
    @PrimaryKey(true)
    val id: Long = 0L,
    val title: String,
    val date: String,
    val period : Int?,
    val notify: Int?,
)

fun ReminderDatabase.toReminder(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        date = (this.date).getFullDate(),
        period = this.period,
        notify = this.notify
    )
}

fun Reminder.toReminderDatabase(): ReminderDatabase {
    return ReminderDatabase(
        id = this.id,
        title = this.title,
        date = (this.date).getStringDate(),
        period = this.period,
        notify = this.notify
    )
}