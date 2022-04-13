package com.nordlocker.storage.todo

import android.content.Context
import android.content.SharedPreferences
import com.nordlocker.domain.models.TodosOrder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val storedOrderKey = "order-type"

// assuming no name mangling from r8 on TodosOrder
class OrderDatabase(context: Context) {

    private val defaultSort = TodosOrder.NOT_COMPLETED

    private val sharedPreferences =
        context.getSharedPreferences("order", Context.MODE_PRIVATE)

    fun getOrder(): TodosOrder =
        TodosOrder.valueOf(
            sharedPreferences.getString(storedOrderKey, defaultSort.name)!!
        )

    fun updateOrder(order: TodosOrder) {
        sharedPreferences.edit()
            .putString(storedOrderKey, order.name)
            .apply()
    }

    fun observe(): Flow<TodosOrder> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (storedOrderKey == key) {
                trySendBlocking(getOrder())
            }
        }

        // send initial
        trySendBlocking(getOrder())

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

}
