package com.example.rsshool2021_android_task_pomodoro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding
import com.example.rsshool2021_android_task_pomodoro.service.TimerNotificationService

class MainActivity : AppCompatActivity(), TimerListAdapter.OnTimerClickListener {

    private lateinit var binding: ActivityMainBinding
    private var viewModel = MainViewModel()
    private var adapter: TimerListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ViewModelRecover.nothingToRecover()) {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            ViewModelRecover.saveForeRecover(viewModel)
        }
        else viewModel = ViewModelRecover.recover()

        viewModel.getData().observe(this, {
            val timerListAdapter = TimerListAdapter(this, it, this)
            binding.timerList.adapter = timerListAdapter
            binding.timerList.layoutManager = LinearLayoutManager(this)
            adapter = timerListAdapter
        })
        binding.addFab.setOnClickListener {
            viewModel.setData(binding.tempMitEdit.text.toString().toInt())
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
        val intent = Intent(this, TimerNotificationService::class.java)
        stopService(intent)
    }

    override fun onPause() {
        if (viewModel.shouldShowNotification()) {
            val intent = Intent(this, TimerNotificationService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        super.onPause()
    }

    override fun onStartOrStopClick() {
    }

    override fun onDeleteClick(position: Int) {
        viewModel.remove(position)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}