package com.nordlocker.android_task.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.android_task.network.TodoApi
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
       viewModelScope.launch(Dispatchers.Default) {
           val loaded = api.getTodoList().toDomain()
           todoStorage.updateOrCreate(loaded.data.orEmpty())
       }
    }

    private val _order = MutableStateFlow(TodosOrder.NOT_COMPLETED)
    val order: Flow<TodosOrder> = _order

    val todos = _order.flatMapLatest { selectedOrder ->
        todoStorage.observeAll(selectedOrder)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        emptyList()
    )

    fun updateOrder(selected: TodosOrder) {
        _order.value = selected
    }
}