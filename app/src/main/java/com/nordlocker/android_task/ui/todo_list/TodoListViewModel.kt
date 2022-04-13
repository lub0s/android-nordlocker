package com.nordlocker.android_task.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.network.TodoApi
import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.TodosOrder
import com.nordlocker.network.response.TodoListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TodosSyncStatus(
    val isLoading: Boolean = false,
)

data class TodosSyncFailed(val throwable: Throwable)

class TodoListViewModel(
    private val todoStorage: TodoStorage,
    private val api: TodoApi,
) : ViewModel() {

    val order = todoStorage.observeOrder()

    val todos = todoStorage.observeAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            emptyList()
        )

    private val _syncEvents = Channel<TodosSyncFailed>(Channel.UNLIMITED)
    val syncEvents = _syncEvents.consumeAsFlow()

    private val _syncStatus = MutableStateFlow(TodosSyncStatus())
    val syncStatus: Flow<TodosSyncStatus> = _syncStatus

    init {
        fetchTodos()
    }

    fun updateOrder(order: TodosOrder) {
        viewModelScope.launch(Dispatchers.Default) {
            todoStorage.updateOrder(order)
        }
    }

    fun fetchTodos() {
        _syncStatus.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.Default) {
            delay(1500L)

            try {
                val loaded = api.getTodoList().toDomain()
                todoStorage.updateOrCreate(loaded.data.orEmpty())
            } catch (throwable: Throwable) {
                _syncEvents.send(TodosSyncFailed(throwable))
            } finally {
                _syncStatus.update { it.copy(isLoading = false) }
            }
        }
    }
}
