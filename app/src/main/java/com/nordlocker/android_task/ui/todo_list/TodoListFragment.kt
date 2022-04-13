package com.nordlocker.android_task.ui.todo_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nordlocker.android_task.R
import com.nordlocker.android_task.databinding.TodoListFragmentBinding
import com.nordlocker.android_task.ui.shared.DividerDecoration
import com.nordlocker.android_task.ui.shared.collectOnStarted
import com.nordlocker.android_task.ui.todo_details.menuTitleRes
import com.nordlocker.domain.models.TodosOrder
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

        collectOnStarted(viewModel.order) { selectedOrder ->
            bindToolbarMenu(binding, selectedOrder)
        }

        collectOnStarted(viewModel.screenState) { screenState ->
            bindScreenState(binding, adapter, screenState)
        }

        collectOnStarted(viewModel.syncEvents) { syncFailed ->
            bindSyncFailed(binding, syncFailed)
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

    private fun bindToolbarMenu(
        binding: TodoListFragmentBinding,
        selectedOrder: TodosOrder,
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
                        updateToSelectedOrder(binding, orderType)
                        true
                    }
                }
            }
        }
    }

    private fun updateToSelectedOrder(
        binding: TodoListFragmentBinding,
        selected: TodosOrder,
    ) {
        viewModel.updateOrder(selected)
        binding.root.postDelayed({ binding.todos.scrollToPosition(0) }, 16 * 3L)
    }

    private fun bindScreenState(
        binding: TodoListFragmentBinding,
        adapter: TodoListAdapter,
        syncStatus: TodosScreenState,
    ) {
        binding.swipeRefreshLayout.isRefreshing = syncStatus.isLoading
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.fetchTodos() }

        val todos = syncStatus.todos
        binding.empty.isVisible = todos.isEmpty()
        adapter.submitList(todos)
    }

    private fun bindSyncFailed(binding: TodoListFragmentBinding, syncFailed: TodosSyncFailed) {
        Snackbar.make(binding.root, R.string.todos_sync_failed, Snackbar.LENGTH_LONG).apply {
            setAction(R.string.todos_sync_failed_action) { viewModel.fetchTodos() }
        }.show()
    }

    private val Toolbar.firstSubmenu
        get() = (menu.getItem(0)).subMenu
}