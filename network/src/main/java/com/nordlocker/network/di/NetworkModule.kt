package com.nordlocker.network.di

import com.nordlocker.network.ApiClient
import com.nordlocker.network.TodoApi
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient() }
    single { TodoApi(client = get()) }
}
