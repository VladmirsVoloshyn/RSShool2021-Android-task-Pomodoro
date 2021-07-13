package com.example.rsshool2021_android_task_pomodoro.model

import android.os.CountDownTimer
import android.util.Log

class Timer(timeInMin: Int = 0, var listener: OnTimeUpdate? = null) {
    var startTimeInMills: Long = (timeInMin * 60000).toLong()
    private var countDownTimer: CountDownTimer? = null
    var isRunning = false
    var isFinished = false
    var timeLeftInMills = startTimeInMills
    var updatableStringTimer = updateCountDownText()

    fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMills = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                isRunning = false
                isFinished = true
                Log.d("FINISH", updatableStringTimer)
                updatableStringTimer = "FINISH"
                listener?.onUpdate("00:00:00")
            }
        }.start()
        isRunning = true
    }

    private fun updateCountDownText(): String {
        val hours: Int = ((timeLeftInMills / 3600000) % 24).toInt()
        val minutes: Int = ((timeLeftInMills / 60000) % 60).toInt()
        val seconds: Int = ((timeLeftInMills / 1000) % 60).toInt()
        val timeLeftFormatter = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        updatableStringTimer = timeLeftFormatter
        listener?.onUpdate(updatableStringTimer)
        Log.d("UPDATE", updatableStringTimer)
        return timeLeftFormatter
    }

    fun stopTimer() {
        if (countDownTimer!=null) {
            countDownTimer?.cancel()
            isRunning = false
        }
    }

    interface OnTimeUpdate {
        fun onUpdate(time: String)
    }
}
