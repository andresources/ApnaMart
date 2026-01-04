package com.apnamart.data.remote.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.data.remote.UserApi
import com.apnamart.data.remote.mapper.toDomain
import com.apnamart.domain.model.RegisterResult
import com.apnamart.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val api: UserApi,
) : UserRepository {

    override fun registerUser(
        name: String,
        address: String,
        phone: Int,
        email: String,
        password: String
    ): Flow<UiState<RegisterResult>> {

        return safeFlowCall {
            api.registerUser(
                name, address, phone, email, password
            ).toDomain()
        }
    }

    override fun loginUser(
        login: String,
        password: String
    ): Flow<UiState<RegisterResult>> {

        return safeFlowCall {
            api.loginUser(
                login, password
            ).toDomain()
        }
    }


}
