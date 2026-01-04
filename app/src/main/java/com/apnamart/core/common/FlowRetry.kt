package com.apnamart.core.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

fun <T> Flow<T>.retryOnNetwork(
    retries: Int = 2,
    delayMillis: Long = 5_000
): Flow<T> =
    retryWhen { cause, attempt ->
        if (cause is IOException && attempt < retries) {
            delay(delayMillis)
            true
        } else {
            false
        }
    }
