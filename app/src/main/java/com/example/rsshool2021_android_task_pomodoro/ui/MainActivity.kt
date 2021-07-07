package com.example.rsshool2021_android_task_pomodoro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), TimerListAdapter.OnTimerClickListener {

    private lateinit var binding: ActivityMainBinding
    private  var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getData().observe(this, Observer {
            val timerListAdapter = TimerListAdapter(this,it, this)
            binding.timerList.adapter = timerListAdapter
            binding.timerList.layoutManager = LinearLayoutManager(this)
        })
        binding.addFab.setOnClickListener {
            viewModel.setData(binding.tempMitEdit.text.toString().toInt())
        }
    }

    override fun onStartOrStopClick() {
    }

    override fun onDeleteClick(position: Int) {
        viewModel.remove(position)
    }
}