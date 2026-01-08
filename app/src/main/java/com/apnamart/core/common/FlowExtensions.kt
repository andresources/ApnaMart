package com.apnamart.core.common

import com.apnamart.core.network.di.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <T> safeFlowCall(
    networkMonitor: NetworkMonitor,
    crossinline apiCall: suspend () -> T
): Flow<UiState<T>> = flow {

    emit(UiState.Loading)
    if (!networkMonitor.isConnected.first()) {
        emit(UiState.Error("Please check internet connection"))
        return@flow
    }
    try {
        val result = apiCall()
        emit(UiState.Success(result))
    } catch (e: Exception) {
        emit(
            UiState.Error(
                message = e.message ?: "Unknown error"
            )
        )
    }
}
