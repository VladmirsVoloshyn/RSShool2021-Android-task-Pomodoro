package com.example.rsshool2021_android_task_pomodoro

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

@Suppress("unused")
class PomodoroApp : Application() {

    companion object {
        const val CHANNEL_ID = "Timer_Channel"
        const val CHANNEL_NAME = "Foreground timer notification"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}