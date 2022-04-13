package com.nordlocker.android_task.ui.todo_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoDetailsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TodoDetailsFragment : Fragment() {

    private val viewModel: TodoDetailsViewModel by viewModel { parametersOf(args.todoId) }
    private val args: TodoDetailsFragmentArgs by navArgs()

    private var binding: TodoDetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TodoDetailsFragmentBinding.inflate(
        LayoutInflater.from(requireContext()),
        container,
        false
    ).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.todo.observe(viewLifecycleOwner) { todo ->
            binding?.message?.text = todo.title
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}