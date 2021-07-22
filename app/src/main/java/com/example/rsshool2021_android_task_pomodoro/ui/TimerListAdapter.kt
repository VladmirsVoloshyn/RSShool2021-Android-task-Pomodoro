package com.example.rsshool2021_android_task_pomodoro.ui

import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.databinding.TimerContainerBinding
import com.example.rsshool2021_android_task_pomodoro.model.Timer
import com.example.rsshool2021_android_task_pomodoro.model.dispatchers.TimerDispatcher
import kotlinx.coroutines.*
import kotlin.concurrent.fixedRateTimer

class TimerListAdapter(
    private val listener: OnTimerClickListener,
    private val timersList: ArrayList<Timer>,
) : RecyclerView.Adapter<TimerListAdapter.ViewHolder>(), Timer.OnTimeUpdate {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TimerContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.vBinding.customProgressBar.show()
        holder.vBinding.customProgressBar.setPeriod(timersList[position].startTimeInMills)
        holder.vBinding.customProgressBar.setCurrent(timersList[position].timeLeftInMills)
        holder.vBinding.timerTextView.text = timersList[position].updatableStringTimer
        holder.vBinding.childContainer.setBackgroundResource(R.drawable.timer_container_bg)
        holder.vBinding.startOrStopButton.enable()

        timersList[position].listener = this

        if (timersList[position].isRunning) {
            holder.animationDrawable.start()

            CoroutineScope(CoroutineName(SIMPLE_THREAD_NAME + position)).launch {
                while (timersList[position].isRunning) {
                    holder.vBinding.timerTextView.text =
                        timersList[position].updatableStringTimer
                    holder.vBinding.customProgressBar.setCurrent(
                        timersList[position].timeLeftInMills
                    )
                }
                delay(100L)
            }
            holder.vBinding.startOrStopButton.changeSelfText(STOP)
            holder.vBinding.animationView.show()
            TimerDispatcher.setTimer(timersList[position])
        }
        if (timersList[position].isFinished) {
            holder.vBinding.childContainer.setBackgroundResource(R.drawable.timer_container_finished_bg)
            holder.vBinding.customProgressBar.hide()
            holder.animationDrawable.stop()
            holder.vBinding.startOrStopButton.unable()
        }
        if (!timersList[position].isRunning) {
            holder.vBinding.startOrStopButton.changeSelfText(START)
            holder.animationDrawable.stop()
            holder.vBinding.animationView.hide()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun onUpdate() {
        notifyDataSetChanged()
    }

    override fun getItemCount() = timersList.size


    inner class ViewHolder(val vBinding: TimerContainerBinding) :
        RecyclerView.ViewHolder(vBinding.root) {
        var animationDrawable: AnimationDrawable

        init {
            vBinding.animationView.setBackgroundResource(R.drawable.running_timer_animation)
            animationDrawable = vBinding.animationView.background as AnimationDrawable
            vBinding.startOrStopButton.setOnClickListener {
                when (timersList[adapterPosition].isRunning) {
                    true -> {
                        timersList[adapterPosition].stopTimer()
                    }
                    false -> {
                        timersList[adapterPosition].startTimer()

                        for (item in timersList) {
                            if (item != timersList[adapterPosition]) {
                                item.stopTimer()
                            }
                        }
                    }
                }
                notifyDataSetChanged()
            }
            vBinding.deleteButton.setOnClickListener {
                timersList[adapterPosition].stopTimer()
                listener.onDeleteClick(adapterPosition)
            }
        }
    }

    interface OnTimerClickListener {
        fun onDeleteClick(position: Int)
    }

    companion object {
        private const val START = "START"
        private const val STOP = "STOP"
        private const val SIMPLE_THREAD_NAME = "Thread"
    }
}