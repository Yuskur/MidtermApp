package com.example.midtermapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {

    //Play button state
    private var _playClicked = MutableLiveData(false)
    val playClicked : LiveData<Boolean>
        get() = _playClicked
    //High score button state
    private var _viewHighScoresClicked = MutableLiveData(false)
    val viewHighScoresClicked : LiveData<Boolean>
        get() = _viewHighScoresClicked

    //If this is triggered it will set the state of the playClicked to true
    fun playBtnClicked(){
        Log.d("playClicked", "Entered")
        _playClicked.value = true
    }
    //If this is triggered it will set the state of the viewHighScoresClicked to true
    fun viewScoresClicked(){
        Log.d("viewScores", "Entered")
        _viewHighScoresClicked.value = true
    }

    //sets all the states back to default
    fun setThingsBack(){
        _playClicked.value = false
        _viewHighScoresClicked.value = false
    }

}