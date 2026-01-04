package com.apnamart.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T> safeFlowCall(
    crossinline apiCall: suspend () -> T
): Flow<UiState<T>> = flow {

    emit(UiState.Loading)

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
