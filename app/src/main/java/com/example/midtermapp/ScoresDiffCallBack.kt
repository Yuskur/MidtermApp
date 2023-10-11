package com.example.midtermapp

import androidx.recyclerview.widget.DiffUtil

//checks the difference in database
class ScoresDiffCallBack : DiffUtil.ItemCallback<Score>(){
    override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return (oldItem == newItem)
    }

}