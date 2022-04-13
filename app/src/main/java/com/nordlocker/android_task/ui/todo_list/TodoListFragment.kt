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
import com.nordlocker.android_task.ui.todo_details.menuTitleRes
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

        val binding = requireNotNull(binding) { "TodoListFragmentBinding is null" }

        val adapter = bindAdapter(binding)

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
                    setOrdersMenu(selectedOrder, binding)
                }
        }
    }

    override fun onDestroyView() {
        binding?.todos?.adapter = null
        binding = null
        super.onDestroyView()
    }

    private fun bindAdapter(binding: TodoListFragmentBinding): TodoListAdapter {
        val adapter = TodoListAdapter { todo ->
            val id = requireNotNull(todo.id) { "Missing todo's id" }
            findNavController().navigate(TodoListFragmentDirections.openDetails(id))
        }

        binding.todos.adapter = adapter
        binding.todos.addItemDecoration(
            DividerDecoration(binding.root.context)
        )

        return adapter
    }

    private fun setOrdersMenu(
        selectedOrder: TodosOrder,
        binding: TodoListFragmentBinding
    ) {
        val orders = TodosOrder.values()

        with(binding.toolbarLayout.toolbar) {
            menu.clear()
            inflateMenu(R.menu.orders)

            orders.forEach { orderType ->
                firstSubmenu.add(orderType.menuTitleRes).apply {
                    isCheckable = true
                    isChecked = orderType == selectedOrder
                    setOnMenuItemClickListener {
                        selectOrder(orderType, binding)
                        true
                    }
                }
            }
        }
    }

    private fun selectOrder(
        selected: TodosOrder,
        binding: TodoListFragmentBinding
    ) {
        viewModel.updateOrder(selected)
        binding.root.postDelayed({ binding.todos.scrollToPosition(0) }, 16 * 3L)
    }

    private val Toolbar.firstSubmenu
        get() = (menu.getItem(0)).subMenu
}