package com.nordlocker.storage.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nordlocker.domain.models.Todo

@Entity(tableName = TodoDao.TABLE_NAME)
class TodoEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val dueDate: String
) {

    companion object {
        fun Todo.toEntity() =
            TodoEntity(
                id = id ?: 0,
                title = title ?: "",
                isCompleted = completed,
                createdAt = createdAt,
                updatedAt = updatedAt,
                dueDate = dueDate ?: ""
            )
    }

    fun toDomain() = Todo(
        id = id,
        title = title,
        completed = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        dueDate = dueDate
    )
}