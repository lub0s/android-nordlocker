package com.nordlocker.storage.todo

import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import com.nordlocker.storage.todo.TodoEntity.Companion.toEntity

class TodoStorageImpl(database: TodoDatabase): TodoStorage {

    private val dao = database.todoDao()

    override suspend fun updateOrCreate(list: List<Todo>) {
        dao.updateOrCreate(list.map { it.toEntity() })
    }

    override suspend fun getAll(): List<Todo> =
        dao.getAll().map { it.toDomain() }
}