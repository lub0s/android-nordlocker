package com.nordlocker.android_task.ui.shared

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


inline fun <T> Fragment.collectOnStarted(flow: Flow<T>, crossinline collector: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { collector(it) }
    }
}