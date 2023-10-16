package com.example.midtermapp

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HighScoresViewModel(val dao: MidtermDao) : ViewModel() {

    //Stores the scores inside of the database, and the states for the text and recycler view visibility
    val scores = dao.getAll()
    var textVisibility = ObservableBoolean()
    var recyclerViewVisibility = ObservableBoolean()

    //If triggered it will display the text "No high scores yet" and disable the recycler view
    fun disableVisibility(){
        textVisibility.set(false)
        recyclerViewVisibility.set(true)
    }
    //If triggered it will display the recycler view and disable the text view "No high scores yet"
    fun enableVisibility(){
        textVisibility.set(true)
        recyclerViewVisibility.set(false)
    }

    //If triggered it will delete the score with the given id from the database
    fun delete(id: Long) {
        dao.get(id).observeForever {
            it?.let {
                viewModelScope.launch {
                    dao.delete(it)
                }
            }
        }
    }

}