package com.example.pawpal.util

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

val Context.notificationManager
    get() = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

val Context.alarmManager
    get() = getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

fun Context.addRemindToAlarm(intent: Intent, timestamp: Long){
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.getBroadcast(
            this,
            3,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    } else {
        PendingIntent.getBroadcast(
            this,
            3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent)
}