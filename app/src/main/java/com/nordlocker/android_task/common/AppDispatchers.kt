package com.nordlocker.android_task.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface AppDispatchers {
    val default: CoroutineContext
}

class DefaultAppDispatchers : AppDispatchers {

    override val default: CoroutineContext
        get() = Dispatchers.Default
}