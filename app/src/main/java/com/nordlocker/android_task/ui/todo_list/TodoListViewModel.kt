package com.nordlocker.android_task.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.network.TodoApi
import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.TodosOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoStorage: TodoStorage,
    private val api: TodoApi,
) : ViewModel() {

    init {
        fetchTodos()
    }

    val order = todoStorage.observeOrder()
    val todos = todoStorage.observeAll()

    fun updateOrder(order: TodosOrder) {
        viewModelScope.launch(Dispatchers.Default) {
            todoStorage.updateOrder(order)
        }
    }

    private fun fetchTodos() {
        viewModelScope.launch(Dispatchers.Default) {
            val loaded = api.getTodoList().toDomain()
            todoStorage.updateOrCreate(loaded.data.orEmpty())
        }
    }
}