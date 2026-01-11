package com.apnamart.feature_auth.data.remote.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.mapper.toDomain
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.model.UserProfile
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import kotlinx.coroutines.flow.Flow

class UserAuthRepositoryImpl(
    private val api: UserApi,
    private val networkMonitor: NetworkMonitor
) : UserAuthRepository {

    override fun registerUser(
        name: String,
        address: String,
        phone: Int,
        email: String,
        password: String
    ): Flow<UiState<Response>> {

        return safeFlowCall(networkMonitor) {
            api.registerUser(
                name, address, phone, email, password
            ).toDomain()
        }
    }

    override fun loginUser(
        login: String,
        password: String
    ): Flow<UiState<Response>> {

        return safeFlowCall(networkMonitor) {
            api.loginUser(
                login, password
            ).toDomain()
        }
    }

    override fun forgotPassword(
        user_email: String,
        new_password: String
    ): Flow<UiState<Response>> {
        return safeFlowCall(networkMonitor) {
            api.forgotPassword(
                user_email, new_password
            ).toDomain()
        }
    }

    override fun getUserProfile(user_email: String): Flow<UiState<UserProfile>> {
        return safeFlowCall(networkMonitor) {
            api.getUserProfile(
                user_email
            ).toDomain()
        }
    }

    override fun updateProfile(
        user_name: String,
        user_address: String,
        user_phone_number: String,
        user_email: String,
        user_password: String
    ): Flow<UiState<Response>> {
        return safeFlowCall(networkMonitor) {
            api.updateUserProfile(
                user_name,
                user_address,
                user_phone_number,
                user_email,
                user_password
            ).toDomain()
        }
    }
}
