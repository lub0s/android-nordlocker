package com.nordlocker.network.response

import com.nordlocker.domain.models.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaResponse(
    @SerialName("pagination")
    val pagination: PaginationResponse
) {
    @Serializable
    data class PaginationResponse(
        @SerialName("total")
        val total: Int? = null,
        @SerialName("pages")
        val pages: Int? = null,
        @SerialName("page")
        val page: Int? = null,
        @SerialName("limit")
        val limit: Int? = null
    )

    fun toDomain() = Meta(
        total = pagination.total,
        pages = pagination.pages,
        page = pagination.page,
        limit = pagination.limit,
    )
}