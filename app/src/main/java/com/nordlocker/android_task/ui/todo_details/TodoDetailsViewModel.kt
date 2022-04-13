package com.nordlocker.android_task.ui.todo_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nordlocker.domain.interfaces.TodoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

private const val todoContentKey = "todo-content-key"

class TodoDetailsViewModel(
    todoId: Int,
    savedStateHandle: SavedStateHandle,
    private val storage: TodoStorage,
) : ViewModel() {

    private val _todo = savedStateHandle.getLiveData<TodoDetail>(todoContentKey)
    val todo: LiveData<TodoDetail> = _todo

    private var pendingUpdateJob: Job? = null

    init {
        if (_todo.value == null) {
            viewModelScope.launch(Dispatchers.Default) {
                _todo.postValue(storage.get(todoId).toDetail())
            }
        }
    }

    fun update(title: String?) {
        updateTodo {
            delay(50L) // debounce
            it.copy(
                title = title,
                updatedAt = Instant.now().toEpochMilli()
            )
        }
    }

    fun markAsCompleted() =
        updateCompleteStatus(true)

    fun markAsNotCompleted() =
        updateCompleteStatus(false)

    private fun updateCompleteStatus(status: Boolean) {
        updateTodo {
            it.copy(
                updatedAt = Instant.now().toEpochMilli(),
                completed = status
            )
        }
    }

    private fun updateTodo(update: suspend (TodoDetail) -> TodoDetail) {
        val todo = requireNotNull(_todo.value) { "Missing todo while updating" }

        pendingUpdateJob?.cancel()

        pendingUpdateJob = viewModelScope.launch(Dispatchers.Default) {
            val updated = update(todo)

            storage.updateOrCreate(listOf(updated.toDomain()))
            _todo.postValue(updated)
        }
    }
}