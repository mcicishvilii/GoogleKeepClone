package com.example.mishokeepclone.common.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mishokeepclone.common.NotificationService

class NotiReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val service = NotificationService(context!!)
        service.showNotificaion(++Counter.value)
    }
}