package com.apnamart.feature_auth.data.remote.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.mapper.toDomain
import com.apnamart.feature_auth.domain.model.RegisterResult
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
    ): Flow<UiState<RegisterResult>> {

        return safeFlowCall(networkMonitor) {
            api.registerUser(
                name, address, phone, email, password
            ).toDomain()
        }
    }

    override fun loginUser(
        login: String,
        password: String
    ): Flow<UiState<RegisterResult>> {

        return safeFlowCall(networkMonitor) {
            api.loginUser(
                login, password
            ).toDomain()
        }
    }
}
