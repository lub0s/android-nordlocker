package com.nordlocker.domain.models

data class TodoList(
    val code: Int? = null,
    val meta: Meta? = null,
    val todos: List<Todo>? = null
)