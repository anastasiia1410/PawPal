package com.example.pawpal.screens.home.medical_page.entity

import java.util.Date

data class Note(
    val notesId: Long = 0L,
    val reminderId: Long,
    val title: String,
    val date: Date,
)