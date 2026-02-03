package com.apnamart.feature_cart.data.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.mapper.toDomain
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import com.apnamart.feature_cart.data.CheckOutApi
import com.apnamart.feature_cart.data.dto.AddCartDto
import com.apnamart.feature_cart.domain.repository.CheckOutRepository
import kotlinx.coroutines.flow.Flow

class CheckOutRepositoryImpl(
    private val api: CheckOutApi,
    private val networkMonitor: NetworkMonitor
) : CheckOutRepository {
    override fun addCartItems(addCartDto: AddCartDto) : Flow<UiState<Response>> {
        return safeFlowCall(networkMonitor) {
            api.addCartItems(
                addCartDto
            ).toDomain()
        }
    }


}