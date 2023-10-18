package com.example.midtermapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MidtermDao {
    @Insert
    suspend fun insert(score: Score)
    @Delete
    suspend fun delete(score: Score)
    @Query("SELECT * FROM score_data WHERE id = :key")
    fun get(key: Long): LiveData<Score>
    @Query("SELECT * FROM score_data ORDER BY score DESC")
    fun getAll(): LiveData<List<Score>>
}