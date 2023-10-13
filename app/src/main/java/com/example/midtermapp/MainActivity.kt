package com.example.midtermapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Makes an instance of the shared viewModel
        val application = application
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        val viewModelFactory = GameViewModelFactory(dao)
    }
}