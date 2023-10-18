package com.example.midtermapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_data")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,  
    @ColumnInfo(name = "player")
    var player: String = "",
    @ColumnInfo(name = "score")
    var score: Int = 0
)