package com.nordlocker.storage.todo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoDatabaseTest {

    private lateinit var todoDao: TodoDao
    private lateinit var db: TodoDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, TodoDatabase::class.java
        ).build()

        todoDao = db.todoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun sampleTest() {
        Assert.assertTrue(true)
    }

    @Test
    fun writeTodos() = runBlocking {
        val now = System.currentTimeMillis()

        val todos = listOf(
            TodoEntity(0, "1", false, now - 1000L, now - 500L, now),
            TodoEntity(1, "2", true, now - 1000L, now - 500L, now),
        )

        todoDao.updateOrCreate(todos)

        Assert.assertEquals(todos[0], todoDao.get(0))
    }
}