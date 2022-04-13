package com.nordlocker.android_task.ui.todo_list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nordlocker.android_task.databinding.TodoItemBinding
import com.nordlocker.domain.models.Todo

private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem
}

class TodoListAdapter(
    private val listener: (Todo) -> Unit
) : ListAdapter<Todo, TodoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

class TodoViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): TodoViewHolder =
            TodoViewHolder(
                TodoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    fun bind(todo: Todo, listener: (Todo) -> Unit) {
        binding.run {
            itemTitleTextView.setOnClickListener { listener(todo) }
            itemTitleTextView.apply {
                text = todo.title
                paintFlags = if (todo.completed) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}