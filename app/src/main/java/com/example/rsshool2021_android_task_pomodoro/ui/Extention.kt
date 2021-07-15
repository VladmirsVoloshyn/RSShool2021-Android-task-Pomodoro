package com.example.rsshool2021_android_task_pomodoro.ui

import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.NumberFormatException


fun TextInputEditText.shouldShowError(textInputLayout: TextInputLayout): Boolean {

    if (text.isNullOrEmpty()) {
        textInputLayout.error = "Set time or dismiss"
        return true
    }

    try {
        text.toString().toInt()
    } catch (e: NumberFormatException) {
        textInputLayout.error =  "This number is too large"
        return true
    }

    if (text.toString().toInt() > 1440) {
        textInputLayout.error = "You cannot set the timer for more than one day (one day = 1440 min)"
        return true
    }

    if (text.toString().toInt() == 0) {
        textInputLayout.error =  "You cannot set the timer for 0 minutes"
        return true
    }

    return false
}

fun Button.changeText(input : String){
    text = input
}

fun ImageView.hide(){
    this.visibility = ImageView.INVISIBLE
}

fun ImageView.show(){
    this.visibility = ImageView.VISIBLE
}

