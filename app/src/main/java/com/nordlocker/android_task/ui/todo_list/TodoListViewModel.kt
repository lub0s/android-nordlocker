package com.nordlocker.android_task.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import com.nordlocker.network.TodoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TodosScreenState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList()
)

data class TodosSyncFailed(
    val throwable: Throwable
)

class TodoListViewModel(
    private val todoStorage: TodoStorage,
    private val api: TodoApi,
) : ViewModel() {

    val order = todoStorage.observeOrder()

    private val todos = todoStorage.observeAll()
    private val isLoading = MutableStateFlow(false)

    private val _syncEvents = Channel<TodosSyncFailed>(Channel.UNLIMITED)
    val syncEvents = _syncEvents.receiveAsFlow()

    val screenState = isLoading.combine(todos) { loading, localTodos ->
        TodosScreenState(isLoading = loading, todos = localTodos)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        TodosScreenState()
    )

    init {
        fetchTodos()
    }

    fun updateOrder(order: TodosOrder) {
        viewModelScope.launch(Dispatchers.Default) {
            todoStorage.updateOrder(order)
        }
    }

    fun fetchTodos() {
        isLoading.update { true }

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val todoList = api.getTodoList().toDomain()
                todoStorage.updateOrCreate(todoList.todos.orEmpty())
            } catch (throwable: Throwable) {
                _syncEvents.send(TodosSyncFailed(throwable))
            } finally {
                isLoading.update { false }
            }
        }
    }
}
