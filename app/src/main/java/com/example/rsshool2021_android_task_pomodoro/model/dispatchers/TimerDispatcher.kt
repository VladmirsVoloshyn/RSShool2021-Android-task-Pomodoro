package com.example.rsshool2021_android_task_pomodoro.model.dispatchers

import com.example.rsshool2021_android_task_pomodoro.model.Timer

object TimerDispatcher {
    private var timer = Timer()

    fun setTimer(_timer: Timer) {
        timer = _timer
    }

    fun getTimer(): Timer {
        return timer
    }
}