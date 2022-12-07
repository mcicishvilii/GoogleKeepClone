package com.example.mishokeepclone.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.mishokeepclone.R

class Notification(
    private val context: Context
){

//    fun showNoti

    companion object{
        const val CHANNEL_ID = "channel"
    }
}