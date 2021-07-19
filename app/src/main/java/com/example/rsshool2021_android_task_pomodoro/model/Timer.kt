package com.example.rsshool2021_android_task_pomodoro.model

import android.os.CountDownTimer

class Timer(minutes: Int = 0, var listener: OnTimeUpdate? = null) {
    var startTimeInMills: Long = (minutes * 60000).toLong()
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
                updatableStringTimer = FINISH
                listener?.onUpdate()
            }
        }.start()
        isRunning = true
    }

    private fun updateCountDownText(): String {
        val hours: Int = ((timeLeftInMills / 3600000) % 24).toInt()
        val minutes: Int = ((timeLeftInMills / 60000) % 60).toInt()
        val seconds: Int = ((timeLeftInMills / 1000) % 60).toInt()
        val timeLeftFormatter = String.format(TIMER_UPDATE_PATTERN, hours, minutes, seconds)


        updatableStringTimer = timeLeftFormatter
        listener?.onUpdate()

        return if (timeLeftInMills == MILLS_IN_DAY) {
            ONE_DAY_PATTERN
        } else {
            timeLeftFormatter
        }
    }

    fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            isRunning = false
        }
    }

    companion object {
        private const val MILLS_IN_DAY = 86400000L
        private const val ONE_DAY_PATTERN = "24:00:00"
        private const val FINISH = "FINISH"
        private const val TIMER_UPDATE_PATTERN = "%02d:%02d:%02d"
    }

    interface OnTimeUpdate {
        fun onUpdate()
    }
}
