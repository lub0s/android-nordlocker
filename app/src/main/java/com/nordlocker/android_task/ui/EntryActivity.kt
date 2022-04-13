package com.nordlocker.android_task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nordlocker.android_task.R
import com.nordlocker.android_task.ui.todo_list.TodoListFragment

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)
    }
}