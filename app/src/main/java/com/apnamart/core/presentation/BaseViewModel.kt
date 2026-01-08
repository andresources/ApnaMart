package com.apnamart.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.core.network.di.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel(
    networkMonitor: NetworkMonitor
) : ViewModel() {

    val isConnected: StateFlow<Boolean> =
        networkMonitor.isConnected.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )
}
