package com.nordlocker.storage

import android.content.Context
import androidx.room.Room
import com.nordlocker.storage.todo.TodoDatabase

object DatabaseCreator {
    fun create(context: Context, name: String = "db"): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, name).build()
}