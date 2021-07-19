package com.example.rsshool2021_android_task_pomodoro.model.dispatchers

import com.example.rsshool2021_android_task_pomodoro.model.Timer

object TimerDispatcher {
    private var timer = Timer()
    var currentIndexInList : Int = 0

    fun setTimer(_timer: Timer, index : Int) {
        timer = _timer
        currentIndexInList = index
    }

    fun getTimer(): Timer {
        return timer
    }
}