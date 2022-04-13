package com.nordlocker.domain.interfaces

import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import kotlinx.coroutines.flow.Flow

interface TodoStorage {
    fun observeAll(): Flow<List<Todo>>
    fun observeOrder(): Flow<TodosOrder>

    suspend fun get(id: Int): Todo
    suspend fun getOrder(): TodosOrder

    suspend fun updateOrCreate(list: List<Todo>)
    suspend fun updateOrder(order: TodosOrder)
}