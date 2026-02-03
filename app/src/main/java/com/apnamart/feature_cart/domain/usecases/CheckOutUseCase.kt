package com.apnamart.feature_cart.domain.usecases

import com.apnamart.core.common.UiState
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import com.apnamart.feature_cart.data.dto.AddCartDto
import com.apnamart.feature_cart.domain.repository.CheckOutRepository
import kotlinx.coroutines.flow.Flow

class CheckOutUseCase(
    private val repository: CheckOutRepository
) {
    operator fun invoke(
        addCartDto: AddCartDto
    ): Flow<UiState<Response>> {
        return repository.addCartItems(
            addCartDto
        )
    }
}