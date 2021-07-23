package com.example.rsshool2021_android_task_pomodoro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding
import com.example.rsshool2021_android_task_pomodoro.service.TimerNotificationService

class MainActivity : AppCompatActivity(), TimerListAdapter.OnTimerClickListener,
    TimeSetDialogFragment.TimeSetDialogListener {

    private lateinit var binding: ActivityMainBinding
    private var viewModel = MainViewModel()
    private var adapter: TimerListAdapter? = null
    private lateinit var intentService: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentService = Intent(applicationContext, TimerNotificationService::class.java)
        stopService(intentService)

        if (ViewModelRecover.nothingToRecover()) {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            ViewModelRecover.saveForeRecover(viewModel)
        } else viewModel = ViewModelRecover.recover()

        viewModel.getData().observe(this, {
            if (adapter == null) {
                adapter = TimerListAdapter(this, it)
                binding.timerList.adapter = adapter
                binding.timerList.layoutManager = LinearLayoutManager(this)
            }
        })
        binding.addFab.setOnClickListener {
            val timeSetDialogFragment = TimeSetDialogFragment()
            timeSetDialogFragment.show(supportFragmentManager, "Set time")
        }
        val swipeToDeleteCallBack = object : SwipeToDeleteCallBack() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val position = viewHolder.adapterPosition
                onDeleteClick(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.timerList)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.timerList.adapter?.notifyDataSetChanged()
        stopService(intentService)
    }

    override fun onDestroy() {
        stopService(intentService)
        super.onDestroy()
    }


    override fun onPause() {
        if (viewModel.shouldShowNotification()) {
            ContextCompat.startForegroundService(this, intentService)
        }
        super.onPause()
    }

    override fun onDeleteClick(position: Int) {
        viewModel.remove(position)
        binding.timerList.adapter?.notifyDataSetChanged()
    }

    override fun onTimeSet(timeInMin: Int) {
        viewModel.setData(timeInMin)
        binding.timerList.adapter?.notifyDataSetChanged()
    }
}