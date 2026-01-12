package com.apnamart.feature_home.domain.usecase

import com.apnamart.core.common.UiState
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_home.domain.model.UserScore
import com.apnamart.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Flow<UiState<List<HomeModel>>> {
        return repository.getHomeDetails()
    }
}