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

class TodoDetailsFragment : Fragment() {

    private val viewModel: TodoDetailsViewModel by viewModel()
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
        // TODO

        binding?.message?.text = "Details Fragment - argument received: ${args.todoId}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}