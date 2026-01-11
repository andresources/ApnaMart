package com.apnamart.feature_home.domain.usecase

import com.apnamart.core.common.UiState
import com.apnamart.feature_home.domain.model.UserScore
import com.apnamart.feature_home.domain.repository.UserScoreRepository
import kotlinx.coroutines.flow.Flow

class UserScoreUseCase(
    private val repository: UserScoreRepository
) {
    suspend operator fun invoke(): Flow<UiState<List<UserScore>>> {
        return repository.getUserScores()
    }
}