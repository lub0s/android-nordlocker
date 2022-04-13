package com.nordlocker.storage.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TodoEntity::class])
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}