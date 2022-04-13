package com.nordlocker.storage.todo

import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import com.nordlocker.storage.todo.TodoEntity.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoStorageImpl(database: TodoDatabase) : TodoStorage {

    private val dao = database.todoDao()

    override suspend fun updateOrCreate(list: List<Todo>) {
        dao.updateOrCreate(list.map { it.toEntity() })
    }

    override suspend fun get(id: Int): Todo =
        dao.get(id).toDomain()

    override fun observeAll(order: TodosOrder): Flow<List<Todo>> =
        when (order) {
            TodosOrder.RECENTLY_UPDATED -> dao.observeRecentlyUpdated()
            TodosOrder.NOT_COMPLETED -> dao.observeCompleted()
        }.map { todos -> todos.map { todo -> todo.toDomain() } }

}