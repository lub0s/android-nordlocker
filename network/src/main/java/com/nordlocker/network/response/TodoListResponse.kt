package com.nordlocker.network.response

import com.nordlocker.domain.models.TodoList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListResponse(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("meta")
    val meta: MetaResponse? = null,
    @SerialName("data")
    val data: List<TodoResponse>? = null
) {

    fun toDomain() = TodoList(
        code = code,
        meta = meta?.toDomain(),
        data = data?.map { it.toDomain() }
    )
}