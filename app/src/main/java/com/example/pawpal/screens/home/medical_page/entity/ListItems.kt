package com.example.pawpal.screens.home.medical_page.entity

sealed class ListItems {
    data class ReminderItem(val reminder: Reminder) : ListItems()
    data class GroupItem(val title: String) : ListItems()
}
