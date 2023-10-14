package com.example.midtermapp

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HighScoresViewModel(val dao: MidtermDao) : ViewModel() {

    val scores = dao.getAll()
    var textVisibility = ObservableBoolean()
    var recyclerViewVisibility = ObservableBoolean()

    fun disableVisibility(){
        textVisibility.set(false)
        recyclerViewVisibility.set(true)
    }
    fun enableVisibility(){
        textVisibility.set(true)
        recyclerViewVisibility.set(false)
    }

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