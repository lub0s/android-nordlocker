package com.nordlocker.android_task.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class ApiClient {
    companion object {
        const val HOST = "gorest.co.in"
    }

    val httpClient = HttpClient {
        expectSuccess = false

        install(JsonFeature) {
            val json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
            serializer = KotlinxSerializer(json)
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
