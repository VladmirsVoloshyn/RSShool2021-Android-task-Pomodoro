package com.example.rsshool2021_android_task_pomodoro.ui

object ViewModelRecover {

    private var viewModel: MainViewModel? = null

    fun saveForeRecover(_viewModel: MainViewModel) {
        viewModel = _viewModel
    }

    fun recover(): MainViewModel {
        return viewModel!!
    }

    fun nothingToRecover(): Boolean {
        return viewModel == null
    }
}