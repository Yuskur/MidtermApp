package com.example.midtermapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel(val dao: MidtermDao) : ViewModel() {
    var name = ""
    var gameScore = 0
    var randomNumber = 0
    var guess = ""
    var guessesSoFar = 0
    var id = 0L

    private var _guessTaken = MutableLiveData(false)
    val guessTaken : LiveData<Boolean>
        get() = _guessTaken

    private var _noName = MutableLiveData(false)
    val noName : LiveData<Boolean>
        get() = _noName
    private var _guessedRight = MutableLiveData(false)
    val guessedRight : LiveData<Boolean>
        get() = _guessedRight
    private fun addScore(){
        viewModelScope.launch {
            val score = Score()
            score.player = "$name score: "
            score.score = gameScore
            dao.insert(score)
            id = score.id
        }
    }
    //
    fun okClicked(){
        Log.d("Clicked", "clicking")
        if(name.isNotEmpty()){
            Log.d("RandomNumber", "$randomNumber")
            _guessTaken.value = true
        } else{
            _noName.value = true
        }
    }
    fun checkAns() : Boolean{
        if(guess.isNotEmpty()) {
            return if (guess.toInt() == randomNumber) {
                addScore()
                true
            } else {
                guessesSoFar++
                false
            }
        }
        return false
    }
    fun onGuessedRight(){
        _guessedRight.value = true
    }
    fun inc(){
        var guessTemp = guess.toInt()
        guessTemp++
        guess = guessTemp.toString()
        Log.d("inc", guess)
    }
    fun dec(){
        var guessTemp = guess.toInt()
        guessTemp--
        guess = guessTemp.toString()
        Log.d("inc", guess)
    }
    fun setBack(){
        _guessTaken.value = false
        _noName.value = false
        _guessedRight.value = false
    }

}