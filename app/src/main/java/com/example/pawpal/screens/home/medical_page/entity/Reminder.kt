package com.example.pawpal.screens.home.medical_page.entity

import java.util.Date

data class Reminder(
    val id: Long = 0L,
    val title: String,
    val date: Date,
    val period : Int?,
    val notify: Int?,
)
