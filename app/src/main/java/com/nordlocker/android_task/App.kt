package com.nordlocker.android_task

import android.app.Application
import com.nordlocker.android_task.di.appModule
import com.nordlocker.storage.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(storageModule, appModule)
        }
    }
}