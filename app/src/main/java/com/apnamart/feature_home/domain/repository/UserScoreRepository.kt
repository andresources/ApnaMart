package com.apnamart.feature_home.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.feature_home.domain.model.UserScore
import kotlinx.coroutines.flow.Flow

interface UserScoreRepository {
    suspend fun getUserScores(): Flow<UiState<List<UserScore>>>
}
