package com.example.pawpal.util

import android.content.Context
import com.example.pawpal.R
import com.example.pawpal.screens.home.medical_page.entity.ListItems
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun groupByListItem(context: Context, reminderList: List<Reminder>): List<ListItems> {
    val actualRemindersList = mutableListOf<ListItems>()
    val periodicReminders = mutableListOf<Reminder>()
    val today = Date()
    reminderList.forEach { reminder ->

        if (reminder.date.after(today) && reminder.period != 0) {
            periodicReminders.add(reminder)
        } else if (reminder.date.after(today)) {
            actualRemindersList.add(ListItems.GroupItem(getGroupTitle(reminder.date)))
            actualRemindersList.add(ListItems.ReminderItem(reminder))
        }
        if (periodicReminders.isNotEmpty()) {
            actualRemindersList.add(
                0,
                ListItems.GroupItem(context.getString(R.string.sometimes_reminder))
            )
            periodicReminders.forEach { remind ->
                actualRemindersList.add(1, ListItems.ReminderItem(remind))
            }
        }
    }

    return actualRemindersList
}


private fun getGroupTitle(date: Date): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
}

fun groupByListItemForHistory(context: Context, reminderList: List<Reminder>): List<ListItems> {
    val historyItems = mutableListOf<ListItems>()
    val today = Calendar.getInstance()
    val reminderDate = Calendar.getInstance()

    val group = reminderList.filter { reminder ->
        reminderDate.time = reminder.date
        reminderDate.before(today) && reminder.period == 0
    }.groupBy { reminder ->
        reminderDate.time = reminder.date
        when (val dif = today.get(Calendar.DAY_OF_YEAR) - reminderDate.get(Calendar.DAY_OF_YEAR)) {
            1 -> context.getString(R.string.yesterday)
            else -> context.getString(R.string.days_ago_pattern, dif)
        }
    }

    group.toSortedMap(compareBy { it }).forEach { (groupItem, groupReminder) ->
        historyItems.add(ListItems.GroupItem(groupItem))
        groupReminder.forEach {
            historyItems.add(ListItems.ReminderItem(it))
        }
    }
    return historyItems
}