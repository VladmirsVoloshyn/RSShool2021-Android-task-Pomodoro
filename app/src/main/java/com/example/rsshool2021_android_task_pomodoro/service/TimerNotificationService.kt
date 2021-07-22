package com.example.rsshool2021_android_task_pomodoro.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.rsshool2021_android_task_pomodoro.PomodoroApp.Companion.CHANNEL_ID
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.model.Timer
import com.example.rsshool2021_android_task_pomodoro.model.dispatchers.TimerDispatcher
import com.example.rsshool2021_android_task_pomodoro.ui.MainActivity
import kotlinx.coroutines.*

class TimerNotificationService : Service(), Timer.OnTimeUpdate, Timer.OnUpdateNotification {

    private var notification: Notification? = null
    private var timerThread = CoroutineScope(CoroutineName("foreground job"))

    override fun onCreate() {
        super.onCreate()
        TimerDispatcher.getTimer().listener = this
        TimerDispatcher.getTimer().notificationListener = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerThread.launch {
            while (TimerDispatcher.getTimer().isRunning) {
                startForeground(
                    1,
                    createNotification(TimerDispatcher.getTimer().updatableStringTimer)
                )
                delay(1000L)
            }
        }
        return START_NOT_STICKY
    }

    override fun onUpdateNotification() {
//        startForeground(1, createNotification(TimerDispatcher.getTimer().updatableStringTimer))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(input: String): Notification {
        val intentActivity = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intentActivity, 0)
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer is running")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .build()
        return notification as Notification
    }

    override fun onUpdate() {
        startForeground(1, createNotification(TimerDispatcher.getTimer().updatableStringTimer))
    }

    override fun onDestroy() {
        timerThread.cancel()
        super.onDestroy()
    }

}