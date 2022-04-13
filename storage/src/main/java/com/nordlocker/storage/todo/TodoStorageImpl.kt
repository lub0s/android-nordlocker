package com.nordlocker.storage.todo

import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import com.nordlocker.storage.todo.TodoEntity.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class TodoStorageImpl(
    todosDatabase: TodoDatabase,
    private val orderDatabase: OrderDatabase
) : TodoStorage {

    private val dao = todosDatabase.todoDao()

    override suspend fun updateOrCreate(list: List<Todo>) {
        dao.updateOrCreate(list.map { it.toEntity() })
    }

    override suspend fun get(id: Int): Todo =
        dao.get(id).toDomain()

    override fun observeAll(): Flow<List<Todo>> =
        orderDatabase.observe()
            .flatMapLatest { order ->
                when (order) {
                    TodosOrder.RECENTLY_UPDATED -> dao.observeRecentlyUpdated()
                    TodosOrder.NOT_COMPLETED -> dao.observeNotCompleted()
                    TodosOrder.COMPLETED -> dao.observeCompleted()
                }.map { todos -> todos.map { todo -> todo.toDomain() } }
            }
            .distinctUntilChanged()

    override fun observeOrder(): Flow<TodosOrder> =
        orderDatabase.observe()
            .distinctUntilChanged()

    override fun getDefaultOrder(): TodosOrder =
        orderDatabase.defaultOrder

    override suspend fun getOrder(): TodosOrder =
        orderDatabase.getOrder()

    override suspend fun updateOrder(order: TodosOrder) {
        orderDatabase.updateOrder(order)
    }
}