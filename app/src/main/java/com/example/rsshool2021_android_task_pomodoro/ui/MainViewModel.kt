package com.example.rsshool2021_android_task_pomodoro.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsshool2021_android_task_pomodoro.data.Timer

class MainViewModel : ViewModel() {

    private var timerData : MutableLiveData<ArrayList<Timer>> = MutableLiveData()
    private val listTimers = ArrayList<Timer>()

    fun setData(time : Int){
        listTimers.add(Timer(time))
        timerData.value = listTimers
        Log.d("This", "${timerData.value?.size}")
    }

    fun getData() : MutableLiveData<ArrayList<Timer>>{
        return timerData
    }


}