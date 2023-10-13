package com.example.midtermapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HighScoresViewModel(val dao: MidtermDao) : ViewModel() {

    val scores = dao.getAll()

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