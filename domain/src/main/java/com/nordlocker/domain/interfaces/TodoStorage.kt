package com.nordlocker.domain.interfaces

import com.nordlocker.domain.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoStorage {
    suspend fun updateOrCreate(list: List<Todo>)
    fun observeAll(): Flow<List<Todo>>
}