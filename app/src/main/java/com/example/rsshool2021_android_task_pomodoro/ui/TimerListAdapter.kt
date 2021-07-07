package com.example.rsshool2021_android_task_pomodoro.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.data.Timer

class TimerListAdapter(
    private val listener: OnTimerClickListener,
    private val timersList: ArrayList<Timer>,
    private val context: Context
) : RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = layoutInflater.inflate(R.layout.timer_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = timersList[position].time.toString()
    }

    override fun getItemCount() = timersList.size


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val textView: TextView = v.findViewById(R.id.timerTextView)
        private var startOrStopButton: Button = v.findViewById(R.id.startOrStopButton)
        var deleteButton: ImageButton = v.findViewById(R.id.deleteButton)

        init {
            v.setOnClickListener(this)
            startOrStopButton.setOnClickListener {
                if (startOrStopButton.text == START) {
                    startOrStopButton.text = STOP
                } else startOrStopButton.text = START
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
        fun onDeleteClick(position : Int)
    }

    companion object {
        private const val START = "START"
        private const val STOP = "STOP"
    }
}