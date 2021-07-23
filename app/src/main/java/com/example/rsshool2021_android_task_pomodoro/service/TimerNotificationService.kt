package com.example.rsshool2021_android_task_pomodoro.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rsshool2021_android_task_pomodoro.PomodoroApp.Companion.CHANNEL_ID
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.model.NotificationTimer
import com.example.rsshool2021_android_task_pomodoro.model.Timer
import com.example.rsshool2021_android_task_pomodoro.model.dispatchers.TimerDispatcher
import com.example.rsshool2021_android_task_pomodoro.ui.MainActivity
import kotlinx.coroutines.*

class TimerNotificationService : Service(), NotificationTimer.OnUpdateNotification {

    private var notification: Notification? = null
    override fun onCreate() {
        super.onCreate()
        TimerDispatcher.getTimer().noteListener = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification(TimerDispatcher.getTimer().updatableStringTimer))
        return START_NOT_STICKY
    }

    override fun onUpdateNotification() {
        startForeground(1, createNotification(TimerDispatcher.getTimer().updatableStringTimer))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(input: String): Notification {
        val bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.clock_notification
        )
        val intentActivity = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intentActivity, 0
        )
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TIMER IS RUNNING")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setLargeIcon(bitmap)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .build()
        return notification as Notification
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}