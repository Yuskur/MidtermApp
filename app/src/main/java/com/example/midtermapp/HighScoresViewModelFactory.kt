package com.example.midtermapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HighScoresViewModelFactory(private val dao: MidtermDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighScoresViewModel::class.java)) {
            return HighScoresViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}