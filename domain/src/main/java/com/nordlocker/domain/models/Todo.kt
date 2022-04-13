package com.nordlocker.domain.models

data class Todo(
    val id: Int? = null,
    val title: String? = null,
    val completed: Boolean,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val dueDate: String? = null
)