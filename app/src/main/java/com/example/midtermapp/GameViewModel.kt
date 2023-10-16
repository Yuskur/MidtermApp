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

    //Text to display the attempts taken so far
    private var _text = MutableLiveData("No attempts so far")
    val text : LiveData<String>
        get() = _text

    //holds the number of guesses taken
    private var _guessTaken = MutableLiveData(false)
    val guessTaken : LiveData<Boolean>
        get() = _guessTaken

    //Determines whether no name has been entered
    private var _noName = MutableLiveData(false)
    val noName : LiveData<Boolean>
        get() = _noName
    //Determines whether the guess taken was the right one
    private var _guessedRight = MutableLiveData(false)
    val guessedRight : LiveData<Boolean>
        get() = _guessedRight

    //Adds the score to the database
    private fun addScore(){
        viewModelScope.launch {
            val score = Score()
            score.player = "$name score: "
            score.score = gameScore
            dao.insert(score)
        }
    }

    /*
    When the OK button is clicked it increments the gameScore sets guess taken to true and changes
    the attempts screen to display the number of attempts so far
     */
    fun okClicked(){
        Log.d("Clicked", "clicking")
        if(name.isNotEmpty()){
            gameScore++
            _guessTaken.value = true
            _text.value = "Attempts so far: $gameScore"
        } else{
            _noName.value = true
        }
    }

    /*
    Checks where the answer given is right and if not it will inc the guesses taken and return
    false
     */
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
    //Increments the typed number
    fun inc(){
        if(guess.get() != null) {
            var guessTemp = guess.get()!!.toInt()
            guessTemp++
            guess.set(guessTemp.toString())
            Log.d("inc", guess.get().toString())
        }
    }
    //Decrements the typed number
    fun dec(){
        if(guess.get() != null) {
            var guessTemp = guess.get()!!.toInt()
            guessTemp--
            guess.set(guessTemp.toString())
            Log.d("inc", guess.get().toString())
        }
    }
    //sets all values back to their original states
    fun setBack(){
        _guessTaken.value = false
        _noName.value = false
        _guessedRight.value = false
    }

}