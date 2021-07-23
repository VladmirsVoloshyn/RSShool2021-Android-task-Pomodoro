package com.example.rsshool2021_android_task_pomodoro.model.dispatchers

import com.example.rsshool2021_android_task_pomodoro.model.NotificationTimer
import com.example.rsshool2021_android_task_pomodoro.model.Timer

object TimerDispatcher {
    private lateinit var timer : NotificationTimer

    fun setTimer(_timer: Timer) {
        timer = NotificationTimer(startTime = _timer.startTimeInMills,leftTime =  _timer.timeLeftInMills)
        timer.startTimer()
    }

    fun getTimer(): NotificationTimer {
        return timer
    }
}