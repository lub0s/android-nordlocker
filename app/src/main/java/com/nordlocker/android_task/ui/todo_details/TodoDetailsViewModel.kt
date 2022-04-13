package com.nordlocker.android_task.ui.todo_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.time.Instant

sealed class DetailsEvent
object Close : DetailsEvent()

class TodoDetailsViewModel(
    todoId: Int,
    private val storage: TodoStorage,
) : ViewModel() {

    private val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo> = _todo

    private val _events = Channel<DetailsEvent>(capacity = Channel.UNLIMITED)
    val events = _events.consumeAsFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _todo.postValue(storage.get(todoId))
        }
    }

    fun update(title: String) {
        updateTodo {
            it.copy(
                title = title,
                updatedAt = Instant.now().toEpochMilli()
            )
        }

        viewModelScope.launch { _events.send(Close) }
    }

    fun complete() {
        updateTodo {
            it.copy(
                updatedAt = Instant.now().toEpochMilli(),
                completed = true
            )
        }

        viewModelScope.launch { _events.send(Close) }
    }

    private fun updateTodo(update: (Todo) -> Todo) {
        val todo = requireNotNull(_todo.value) { "Missing todo while updating" }

        viewModelScope.launch(Dispatchers.Default) {
            storage.updateOrCreate(
                listOf(update(todo))
            )
        }
    }
}