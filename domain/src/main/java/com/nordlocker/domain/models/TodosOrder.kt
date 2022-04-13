package com.nordlocker.domain.models

enum class TodosOrder {
    RECENTLY_UPDATED,
    NOT_COMPLETED;

    fun queryFieldName(): String = when(this) {
        RECENTLY_UPDATED -> "updatedAt"
        NOT_COMPLETED -> "status"
    }
}