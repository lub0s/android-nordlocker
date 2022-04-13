package com.nordlocker.android_task.ui.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoDetailsFragmentBinding
import com.nordlocker.android_task.databinding.TodoListFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment() {

    private val viewModel: TodoListViewModel by viewModel()
    private var binding: TodoListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TodoListFragmentBinding.inflate(
        LayoutInflater.from(requireContext()),
        container,
        false
    ).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoListAdapter { todo ->
            val id = requireNotNull(todo.id) { "Missing todo's id" }
            findNavController().navigate(TodoListFragmentDirections.openDetails(id))
        }

        val binding = requireNotNull(binding) { "TodoListFragmentBinding is null" }
        binding.todos.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todos
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { todos ->
                    binding.empty.isVisible = todos.isEmpty()
                    adapter.submitList(todos)
                }
        }
    }

    override fun onDestroyView() {
        binding?.todos?.adapter = null
        binding = null
        super.onDestroyView()
    }
}