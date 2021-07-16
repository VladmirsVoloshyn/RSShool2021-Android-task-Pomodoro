package com.example.rsshool2021_android_task_pomodoro.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsshool2021_android_task_pomodoro.model.Timer
import org.w3c.dom.Text

class MainViewModel(some : String) : ViewModel() {

    private var timerData: MutableLiveData<ArrayList<Timer>> = MutableLiveData()
    private val listTimers = ArrayList<Timer>()
    
    fun setData(time: Int) {
        listTimers.add(Timer(time))
        timerData.value = listTimers
    }

    fun remove(position: Int) {
        listTimers.removeAt(position)
        timerData.value = listTimers
    }

    fun getData(): MutableLiveData<ArrayList<Timer>> {
        return timerData
    }

    fun shouldShowNotification(): Boolean {
        for (timer in listTimers) {
            if (timer.isRunning) {
                return true
            }
        }
        return false
    }

}