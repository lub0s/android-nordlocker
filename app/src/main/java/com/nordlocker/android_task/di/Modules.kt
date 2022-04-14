package com.nordlocker.android_task.di

import com.nordlocker.android_task.common.AppDispatchers
import com.nordlocker.android_task.common.DefaultAppDispatchers
import com.nordlocker.android_task.ui.todo_details.TodoDetailsViewModel
import com.nordlocker.android_task.ui.todo_list.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AppDispatchers> { DefaultAppDispatchers() }

    viewModel { TodoListViewModel(get(), get(), get()) }
    viewModel { parameters -> TodoDetailsViewModel(parameters.get(), get(), get(), get()) }
}