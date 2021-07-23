package com.example.rsshool2021_android_task_pomodoro.model

import android.os.CountDownTimer


open class Timer(
    minutes: Int = 0,
    var listener : OnTimerFinish? = null
) {
    var startTimeInMills: Long = (minutes * 60000).toLong()
    open var countDownTimer: CountDownTimer? = null
    var isRunning = false
    var isFinished = false
    var isStopped = false
    var timeLeftInMills = startTimeInMills
    var updatableStringTimer = this.updateCountDownText()
    open var countDownInterval = 1L


   open fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMills, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMills = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                isRunning = false
                isFinished = true
                updatableStringTimer = FINISH
                listener?.onTimerFinish()
            }
        }.start()
        isRunning = true
    }

   open fun updateCountDownText(): String {
        val hours: Int = ((timeLeftInMills / 3600000) % 24).toInt()
        val minutes: Int = ((timeLeftInMills / 60000) % 60).toInt()
        val seconds: Int = ((timeLeftInMills / 1000) % 60).toInt()
        val timeLeftFormatter = String.format(TIMER_UPDATE_PATTERN, hours, minutes, seconds)
        updatableStringTimer = timeLeftFormatter
        return if (timeLeftInMills == MILLS_IN_DAY) {
            ONE_DAY_PATTERN
        } else {
            timeLeftFormatter
        }
    }

   open fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            isRunning = false
            isStopped = true
        }
    }

    companion object {
        private const val MILLS_IN_DAY = 86400000L
        private const val ONE_DAY_PATTERN = "24:00:00"
        private const val FINISH = "FINISH"
        private const val TIMER_UPDATE_PATTERN = "%02d:%02d:%02d"
    }


    interface OnTimerFinish {
        fun onTimerFinish()
    }
}
