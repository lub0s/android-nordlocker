package com.nordlocker.android_task.todo_list

import app.cash.turbine.test
import com.nordlocker.android_task.common.AppDispatchers
import com.nordlocker.android_task.ui.todo_list.TodoListViewModel
import com.nordlocker.domain.interfaces.TodoStorage
import com.nordlocker.domain.models.Todo
import com.nordlocker.domain.models.TodosOrder
import com.nordlocker.network.TodoApi
import com.nordlocker.network.response.TodoListResponse
import com.nordlocker.network.response.TodoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class TodoListViewModelTest {

    private lateinit var mockResponse: TodoResponse
    private lateinit var todoStorage: TodoStorage
    private lateinit var todoApi: TodoApi
    private lateinit var dispatchers: AppDispatchers
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setupMocks() {
        todoStorage = object : TodoStorage {
            private val inMemoryStore = MutableStateFlow<List<Todo>>(emptyList())

            override fun observeAll(): Flow<List<Todo>> =
                inMemoryStore

            override fun observeOrder(): Flow<TodosOrder> =
                flow { emit(getDefaultOrder()) }

            override suspend fun get(id: Int): Todo =
                inMemoryStore.value.first { todo -> todo.id == id }

            override fun getDefaultOrder(): TodosOrder =
                TodosOrder.NOT_COMPLETED

            override suspend fun getOrder(): TodosOrder =
                getDefaultOrder()

            override suspend fun updateOrCreate(list: List<Todo>) {
                inMemoryStore.update { list }
            }

            override suspend fun updateOrder(order: TodosOrder) {
                TODO("Not yet implemented")
            }
        }

        todoApi = object : TodoApi {
            override suspend fun getTodoList() = TodoListResponse(
                200, null, listOf(mockResponse)
            )
        }

        mockResponse = TodoResponse(
            0,
            "a",
            "completed",
            "2022-05-08T00:00:00.000+05:30"
        )

        testDispatcher = StandardTestDispatcher()

        dispatchers = object : AppDispatchers {
            override val default: CoroutineContext
                get() = testDispatcher
        }

        Dispatchers.setMain(testDispatcher) // because viewmodels :shrug:
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateOrder() = runBlocking {
        val viewModel = TodoListViewModel(
            todoStorage,
            todoApi,
            dispatchers
        )

        viewModel.screenState.test {
            assertTrue(awaitItem().todos.isEmpty())
            testDispatcher.scheduler.advanceUntilIdle()

            val afterReadFromDb = awaitItem().todos
            assertTrue(afterReadFromDb.isNotEmpty())
            assertTrue(afterReadFromDb.first().id == mockResponse.id)
        }
    }
}