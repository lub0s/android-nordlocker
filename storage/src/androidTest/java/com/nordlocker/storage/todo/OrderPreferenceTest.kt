package com.nordlocker.storage.todo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.nordlocker.domain.models.TodosOrder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OrderPreferenceTest {

    lateinit var orderPreference: OrderPreference

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        orderPreference = OrderPreference(context)
    }

    @Test
    fun testObserve() = runBlocking {
        orderPreference.observe().test {
            assertEquals(orderPreference.defaultOrder, awaitItem())
            orderPreference.updateOrder(TodosOrder.COMPLETED)
            assertEquals(TodosOrder.COMPLETED, awaitItem())
            cancel()
        }
    }
}