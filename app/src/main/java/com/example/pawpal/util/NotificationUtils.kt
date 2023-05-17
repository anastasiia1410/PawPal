package com.example.pawpal.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pawpal.R
import com.example.pawpal.screens.main.MainActivity

const val NOTIFICATION_CHANNEL_ID = "channel_reminder"
const val ACTION = "com.example.pawpal.screens.reminderMY_ACTION"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        context.notificationManager.createNotificationChannel(notificationChannel)
    }
}

fun createNotification(context: Context, text: String): Notification {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                context, 2, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle("New reminder")
        .setContentText(text)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_paw_print)
        .setAutoCancel(true)
        .build()
}