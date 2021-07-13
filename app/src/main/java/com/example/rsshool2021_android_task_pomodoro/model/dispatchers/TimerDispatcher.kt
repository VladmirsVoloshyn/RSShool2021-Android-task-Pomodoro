package com.example.rsshool2021_android_task_pomodoro.model.dispatchers

import android.util.Log
import com.example.rsshool2021_android_task_pomodoro.model.Timer

object TimerDispatcher {
    private var timer = Timer()

    fun setTimer(_timer: Timer) {
        timer = _timer
        Log.d("TimerDispatcher", timer.updatableStringTimer)
    }

    fun getTimer(): Timer {
        return timer
    }
}