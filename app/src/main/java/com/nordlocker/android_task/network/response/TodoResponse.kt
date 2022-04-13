package com.nordlocker.android_task.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
)