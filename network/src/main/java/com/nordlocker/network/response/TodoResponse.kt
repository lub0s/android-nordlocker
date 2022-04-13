package com.nordlocker.network.response

import com.nordlocker.domain.models.Todo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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
    fun toDomain(): Todo {
        val dueDate = ZonedDateTime.parse(dueOn).toInstant()

        // faking data a bit
        val random = Random(System.currentTimeMillis())
        val createdAt = dueDate.minusMillis(TimeUnit.DAYS.toMillis(random.nextLong(1, 10)))
        val updatedAt = dueDate.minusMillis(TimeUnit.HOURS.toMillis(random.nextLong(0, 3)))

        return Todo(
            id = id,
            title = title,
            completed = status == "completed",
            createdAt = createdAt.toEpochMilli(),
            updatedAt = updatedAt.toEpochMilli(),
            dueDate = dueDate.toEpochMilli(),
        )
    }
}