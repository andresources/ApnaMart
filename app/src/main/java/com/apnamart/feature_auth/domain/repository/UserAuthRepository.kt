package com.apnamart.feature_auth.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    fun registerUser(
        name: String,
        address: String,
        phone: Int,
        email: String,
        password: String
    ): Flow<UiState<Response>>

    fun loginUser(
        login: String,
        password: String
    ): Flow<UiState<Response>>

    fun forgotPassword(
        user_email: String,
        new_password: String
    ): Flow<UiState<Response>>

    fun getUserProfile(
        user_email: String
    ): Flow<UiState<UserProfile>>

    fun updateProfile(
        user_name: String,
        user_address: String,
        user_phone_number: String,
        user_email: String,
        user_password: String
    ): Flow<UiState<Response>>
}