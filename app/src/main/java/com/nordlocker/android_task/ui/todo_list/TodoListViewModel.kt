package com.nordlocker.android_task.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.android_task.network.TodoApi
import com.nordlocker.domain.interfaces.TodoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    val todos = todoStorage.observeAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            emptyList()
        )
}