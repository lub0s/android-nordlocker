package com.nordlocker.android_task.ui.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoDetailsFragmentBinding
import com.nordlocker.android_task.databinding.TodoListFragmentBinding
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

        val binding = binding!!
        val adapter = TodoListAdapter { todo ->
            val id = requireNotNull(todo.id) { "Missing todo's id" }
            findNavController().navigate(TodoListFragmentDirections.openDetails(id))
        }

        binding.todos.adapter = adapter
    }

    override fun onDestroyView() {
        binding?.todos?.adapter = null
        binding = null
        super.onDestroyView()
    }
}