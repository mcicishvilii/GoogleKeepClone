package com.example.mishokeepclone.common

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.notif.NotiReceiver
import com.example.mishokeepclone.ui.screens.add_task.AddTaskFragment


class NotificationService
    (
    private val context: Context
)
{
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotificaion(counter:Int){

        val activityIntent  = Intent(context,AddTaskFragment::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context,NotiReceiver::class.java),
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.google_keep_icon__2020_)
            .setContentTitle("gamarjoba")
            .setContentText("es aris teqsti gilocav es tu chans!")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.google_keep_icon__2020_,
                "increment",
                incrementIntent
            )
            .build()

        notificationManager.notify(1, notification)

    }

    companion object{
        const val CHANNEL_ID = "channel"
    }
}