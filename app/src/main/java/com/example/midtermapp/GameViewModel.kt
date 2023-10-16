package com.example.midtermapp

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel(val dao: MidtermDao) : ViewModel() {
    //storing the name and gameScore
    var name = ""
    var gameScore = 0
    //stores the randomNumber
    var randomNumber = 0
    //stores the guess the user has entered in the editText view
    var guess = ObservableField<String>()
    //Stores the number of guesses made so far
    private var guessesSoFar = 0
    //
    var id = 0L
    private var _text = MutableLiveData("No attempts so far")
    val text : LiveData<String>
        get() = _text

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
        }
    }

    fun okClicked(){
        Log.d("Clicked", "clicking")
        if(name.isNotEmpty()){
            Log.d("RandomNumber", "$randomNumber")
            gameScore++
            _guessTaken.value = true
            _text.value = "Attempts so far: $gameScore"
        } else{
            _noName.value = true
        }
    }
    fun checkAns() : Boolean{
        if(guess.get() != null) {
            if (guess.get()!!.isNotEmpty()) {
                return if (guess.get()!!.toInt() == randomNumber) {
                    addScore()
                    true
                } else {
                    guessesSoFar++
                    false
                }
            }
        }
        return false
    }
    fun onGuessedRight(){
        _guessedRight.value = true
    }
    fun inc(){
        if(guess.get() != null) {
            var guessTemp = guess.get()!!.toInt()
            guessTemp++
            guess.set(guessTemp.toString())
            Log.d("inc", guess.get().toString())
        }
    }
    fun dec(){
        if(guess.get() != null) {
            var guessTemp = guess.get()!!.toInt()
            guessTemp--
            guess.set(guessTemp.toString())
            Log.d("inc", guess.get().toString())
        }
    }
    fun setBack(){
        _guessTaken.value = false
        _noName.value = false
        _guessedRight.value = false
    }

}