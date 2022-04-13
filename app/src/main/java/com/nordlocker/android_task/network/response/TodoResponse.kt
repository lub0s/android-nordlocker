package com.nordlocker.android_task.network.response

import com.nordlocker.domain.models.Todo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Serializable
data class TodoResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("due_on")
    val dueOn: String? = null
) {
    fun toDomain() = Todo(
        id = id,
        title = title,
        completed = status == "completed",
        createdAt = null,
        updatedAt = null,
        dueDate = ZonedDateTime.parse(dueOn).toInstant().toEpochMilli(),
    )
}