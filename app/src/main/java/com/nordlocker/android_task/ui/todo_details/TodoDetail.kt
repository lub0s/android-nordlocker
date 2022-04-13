package com.nordlocker.android_task.ui.todo_details

import com.nordlocker.android_task.R
import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import kotlinx.serialization.Serializable

@Serializable
data class TodoDetail(
    val id: Int? = null,
    val title: String? = null,
    val completed: Boolean,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val dueDate: Long? = null
) {

    fun toDomain() = Todo(
        id = id,
        title = title,
        completed = completed,
        createdAt = createdAt,
        updatedAt = updatedAt,
        dueDate = dueDate,
    )
}

fun Todo.toDetail() = TodoDetail(
    id = id,
    title = title,
    completed = completed,
    createdAt = createdAt,
    updatedAt = updatedAt,
    dueDate = dueDate,
)

internal val TodosOrder.menuTitleRes
    get() = when (this) {
        TodosOrder.RECENTLY_UPDATED -> R.string.filter_recent
        TodosOrder.NOT_COMPLETED -> R.string.filter_not_completed
        TodosOrder.COMPLETED -> R.string.filter_completed
    }
