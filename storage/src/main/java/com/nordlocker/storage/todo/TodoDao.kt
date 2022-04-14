package com.nordlocker.storage.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    companion object {
        const val TABLE_NAME = "todo"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrCreate(list: List<TodoEntity>)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY isCompleted ASC")
    fun observeNotCompleted(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE isCompleted = 0")
    fun observeOnlyNotCompleted(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY isCompleted DESC")
    fun observeCompleted(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE isCompleted = 1")
    fun observeOnlyCompleted(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY updatedAt")
    fun observeRecentlyUpdated(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE dueDate >= :current ORDER BY updatedAt")
    fun observeClosestTo(current: Long): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: Int): TodoEntity
}
