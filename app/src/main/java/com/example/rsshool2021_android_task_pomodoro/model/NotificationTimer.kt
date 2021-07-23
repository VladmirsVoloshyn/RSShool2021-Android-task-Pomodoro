package com.example.rsshool2021_android_task_pomodoro.model

import android.os.CountDownTimer

class NotificationTimer(var noteListener: OnUpdateNotification? = null, startTime : Long, leftTime : Long) : Timer() {

    private var countDownTimer: CountDownTimer? = null
    override var countDownInterval = 1000L

    init {
        startTimeInMills = startTime
        timeLeftInMills = leftTime
    }


    override fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMills, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMills = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                isRunning = false
                isFinished = true
                updatableStringTimer = FINISH
            }
        }.start()
        isRunning = true
    }

    override fun updateCountDownText(): String {
        val hours: Int = ((timeLeftInMills / 3600000) % 24).toInt()
        val minutes: Int = ((timeLeftInMills / 60000) % 60).toInt()
        val seconds: Int = ((timeLeftInMills / 1000) % 60).toInt()
        val timeLeftFormatter = String.format(TIMER_UPDATE_PATTERN, hours, minutes, seconds)

        updatableStringTimer = timeLeftFormatter
        noteListener?.onUpdateNotification()

        return if (timeLeftInMills == MILLS_IN_DAY) {
            ONE_DAY_PATTERN
        } else {
            timeLeftFormatter
        }
    }

    override fun stopTimer() {
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

    interface OnUpdateNotification {
        fun onUpdateNotification()
    }
}