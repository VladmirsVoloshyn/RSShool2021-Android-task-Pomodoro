package com.example.rsshool2021_android_task_pomodoro.ui


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsshool2021_android_task_pomodoro.model.Timer

class MainViewModel : ViewModel() {

    private var timerData: MutableLiveData<ArrayList<Timer>> = MutableLiveData()
    private val listTimers: java.util.ArrayList<Timer> = ArrayList()

    fun setData(time: Int) {
        listTimers.add(Timer(time))
        timerData.postValue(listTimers)
        getStatus()
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
    private fun getStatus (){
        for (time in listTimers){
            Log.d(listTimers.indexOf(time).toString(), time.isFinished.toString())
        }
    }

}