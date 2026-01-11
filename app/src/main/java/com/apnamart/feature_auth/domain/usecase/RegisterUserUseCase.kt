package com.apnamart.feature_auth.domain.usecase

import com.apnamart.core.common.UiState
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.model.UserProfile
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
    ): Flow<UiState<Response>> {
        return repository.registerUser(
            name, address, phone, email, password
        )
    }

    operator fun invoke(
        login: String,
        password: String
    ): Flow<UiState<Response>> {
        return repository.loginUser(
            login, password
        )
    }

    fun invokeFP(
        user_email: String,
        new_password: String
    ): Flow<UiState<Response>> {
        return repository.forgotPassword(
            user_email, new_password
        )
    }

    fun invoke(
        user_email: String
    ): Flow<UiState<UserProfile>> {
        return repository.getUserProfile(
            user_email
        )
    }

    fun invoke(
        user_name: String,
        user_address: String,
        user_phone_number: String,
        user_email: String,
        user_password: String
    ): Flow<UiState<Response>> {
        return repository.updateProfile(
            user_name = user_name,
            user_address = user_address,
            user_phone_number = user_phone_number,
            user_email = user_email,
            user_password = user_password
        )
    }
}