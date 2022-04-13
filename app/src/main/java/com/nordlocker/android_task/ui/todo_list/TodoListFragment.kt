package com.nordlocker.android_task.ui.todo_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoListFragmentBinding
import com.nordlocker.android_task.ui.shared.DividerDecoration
import com.nordlocker.domain.models.TodosOrder
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
        binding.todos.addItemDecoration(
            DividerDecoration(view.context)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todos
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { todos ->
                    binding.empty.isVisible = todos.isEmpty()
                    adapter.submitList(todos)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.order
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { selectedOrder ->
                    setOrdersMenu(selectedOrder)
                }
        }
    }

    override fun onDestroyView() {
        binding?.todos?.adapter = null
        binding = null
        super.onDestroyView()
    }

    private fun setOrdersMenu(selectedOrder: TodosOrder) {
        val orders = TodosOrder.values()
        val binding = requireNotNull(binding) { "TodoListFragmentBinding is null" }

        with(binding.toolbarLayout.toolbar) {
            menu.clear()
            inflateMenu(R.menu.orders)

            orders.forEach { orderType ->
                firstSubmenu.add(orderType.menuTitleRes).apply {
                    isCheckable = true
                    isChecked = orderType == selectedOrder
                    setOnMenuItemClickListener { viewModel.updateOrder(orderType); true }
                }
            }
        }
    }


    private val Toolbar.firstSubmenu get() = (menu.getItem(0)).subMenu
    private val TodosOrder.menuTitleRes get() = when (this) {
        TodosOrder.RECENTLY_UPDATED -> "Recently updated"
        TodosOrder.NOT_COMPLETED -> "Not completed"
    }
}