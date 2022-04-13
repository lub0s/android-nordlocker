package com.nordlocker.domain.interfaces

import com.nordlocker.domain.models.Todo

interface TodoStorage {
    suspend fun updateOrCreate(list: List<Todo>)
    suspend fun getAll(): List<Todo>
}