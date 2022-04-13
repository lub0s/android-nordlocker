package com.nordlocker.android_task.network

import com.nordlocker.android_task.network.response.TodoListResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TodoApi(private val client: ApiClient) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getTodoList(): TodoListResponse = getResult(
        onCall = { client.httpClient.get { url { encodedPath = "public-api/todos" } } }
    )

    private suspend inline fun <reified T : Any> getResult(
        crossinline onCall: suspend () -> HttpResponse,
    ): T {
        val response = onCall()

        if (!response.status.isSuccess()) throw Exception("Error")
        else return json.decodeFromString(response.readText())
    }
}