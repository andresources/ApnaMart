package com.apnamart.feature_cart.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_cart.data.dto.AddCartDto
import kotlinx.coroutines.flow.Flow

interface CheckOutRepository {
    fun addCartItems(addCartDto: AddCartDto) : Flow<UiState<Response>>
}