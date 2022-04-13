package com.nordlocker.android_task.ui.todo_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoDetailsFragmentBinding
import com.nordlocker.android_task.ui.shared.strikedThruIf
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

        bindToolbar()

        viewModel.todo.observe(viewLifecycleOwner) { todo ->
            bindTodo(todo)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun bindToolbar() {
        val binding = requireNotNull(binding) { "TodoDetailsFragmentBinding is null" }

        binding.toolbarLayout.toolbar.navigationIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_back)

        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindTodo(todoDetail: TodoDetail) {
        val binding = requireNotNull(binding) { "TodoDetailsFragmentBinding is null" }

        binding.message.apply {
            text = todoDetail.title
            strikedThruIf(todoDetail.completed)
        }

        binding.complete.text = when (todoDetail.completed) {
            true -> "mark as not complete"
            false -> "mark as complete"
        }

        binding.complete.setOnClickListener {
            when (todoDetail.completed) {
                true -> viewModel.markAsNotCompleted()
                false -> viewModel.markAsCompleted()
            }
        }
    }
}