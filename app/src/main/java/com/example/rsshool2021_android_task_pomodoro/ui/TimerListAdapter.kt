package com.example.rsshool2021_android_task_pomodoro.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.model.Timer

class TimerListAdapter(
    private val listener: OnTimerClickListener,
    private val timersList: ArrayList<Timer>,
    context: Context
) : RecyclerView.Adapter<TimerListAdapter.ViewHolder>(),Timer.OnTimeUpdate {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.timer_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        timersList[position].listener = this
        holder.textView.text = timersList[position].updatableStringTimer
        if (timersList[position].isRunning){
            holder.startOrStopButton.text = STOP
        }else holder.startOrStopButton.text = START

    }
    override fun onUpdate(time: String) {
        notifyDataSetChanged()
    }

    override fun getItemCount() = timersList.size


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val textView: TextView = v.findViewById(R.id.timerTextView)
        var startOrStopButton: Button = v.findViewById(R.id.startOrStopButton)
        var deleteButton: ImageButton = v.findViewById(R.id.deleteButton)

        init {
            v.setOnClickListener(this)
            startOrStopButton.setOnClickListener {
                if (timersList[adapterPosition].isRunning) {
                    timersList[adapterPosition].stopTimer()
                    startOrStopButton.text = START
                } else {
                    if (!timersList[adapterPosition].isRunning) {
                        timersList[adapterPosition].startTimer()
                        startOrStopButton.text = STOP
                    }
                }
                listener.onStartOrStopClick()
            }
            deleteButton.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
            }
        }
    }

    interface OnTimerClickListener {
        fun onStartOrStopClick()
        fun onDeleteClick(position: Int)
    }

    companion object {
        private const val START = "START"
        private const val STOP = "STOP"
    }




}