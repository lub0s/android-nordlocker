package com.nordlocker.storage.di

import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.storage.DatabaseCreator
import com.nordlocker.storage.todo.OrderPreference
import com.nordlocker.storage.todo.TodoStorageImpl
import org.koin.dsl.module

val storageModule = module {
    single { DatabaseCreator.create(get()) }
    single { OrderPreference(get()) }
    single<TodoStorage> { TodoStorageImpl(todosDatabase = get(), orderDatabase = get()) }
}
