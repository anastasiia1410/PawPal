package com.example.pawpal.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pawpal.screens.home.medical_page.screens.reminder.create_reminder.CreateReminderFragment.Companion.TITLE_KEY
import com.example.pawpal.util.createNotification
import com.example.pawpal.util.notificationManager

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        if(bundle != null){
            val notifyName = bundle.getString(TITLE_KEY)
            val notification = createNotification(context, notifyName!!)
            val id = System.currentTimeMillis().toInt()
            context.notificationManager.notify(id, notification)
            createNotification(context, notifyName)
        }
    }
}