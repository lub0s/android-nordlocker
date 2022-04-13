package com.nordlocker.storage.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    companion object {
        const val TABLE_NAME = "todo"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrCreate(list: List<TodoEntity>)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAll(): List<TodoEntity>
}