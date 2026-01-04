package com.apnamart.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.domain.model.RegisterResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun registerUser(
        name: String,
        address: String,
        phone: Int,
        email: String,
        password: String
    ): Flow<UiState<RegisterResult>>

    fun loginUser(
        login: String,
        password: String
    ): Flow<UiState<RegisterResult>>
}
