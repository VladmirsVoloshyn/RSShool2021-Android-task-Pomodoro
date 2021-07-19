package com.example.rsshool2021_android_task_pomodoro.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.rsshool2021_android_task_pomodoro.databinding.MinSetDialogBinding

class TimeSetDialogFragment : DialogFragment() {

    private var _binding: MinSetDialogBinding? = null
    private val binding get() = _binding!!
    private var listener: TimeSetDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MinSetDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.okButton.setOnClickListener {

            when {
                binding.minEditText.shouldShowError(binding.textInputLayout) -> {
                }
                else -> {
                    listener?.onTimeSet(binding.minEditText.text.toString().toInt())
                    dialog?.dismiss()
                }
            }
        }

        binding.dismissButton.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as TimeSetDialogListener
    }

    override fun onDestroyView() {
        _binding = null
        listener = null
        super.onDestroyView()
    }

    interface TimeSetDialogListener {
        fun onTimeSet(timeInMin: Int)
    }
}