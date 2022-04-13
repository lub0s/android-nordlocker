package com.nordlocker.domain.interfaces

import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import kotlinx.coroutines.flow.Flow

interface TodoStorage {
    fun observeAll(order: TodosOrder): Flow<List<Todo>>

    suspend fun get(id: Int): Todo
    suspend fun updateOrCreate(list: List<Todo>)
}