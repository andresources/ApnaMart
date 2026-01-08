package com.apnamart.feature_auth.domain.usecase

import com.apnamart.core.common.UiState
import com.apnamart.feature_auth.domain.model.RegisterResult
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(
    private val repository: UserAuthRepository
) {
    operator fun invoke(
        name: String,
        address: String,
        phone: Int,
        email: String,
        password: String
    ): Flow<UiState<RegisterResult>> {
        return repository.registerUser(
            name, address, phone, email, password
        )
    }

    operator fun invoke(
        login: String,
        password: String
    ): Flow<UiState<RegisterResult>> {
        return repository.loginUser(
            login, password
        )
    }
}