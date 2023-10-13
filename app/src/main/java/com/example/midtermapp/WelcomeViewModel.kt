package com.example.midtermapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {

    private var _playClicked = MutableLiveData(false)
    val playClicked : LiveData<Boolean>
        get() = _playClicked
    private var _viewHighScoresClicked = MutableLiveData(false)
    val viewHighScoresClicked : LiveData<Boolean>
        get() = _viewHighScoresClicked
    fun playBtnClicked(){
        Log.d("playClicked", "Entered")
        _playClicked.value = true
    }
    fun viewScoresClicked(){
        Log.d("viewScores", "Entered")
        _viewHighScoresClicked.value = true
    }
    fun setThingsBack(){
        _playClicked.value = false
        _viewHighScoresClicked.value = false
    }

}