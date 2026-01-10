package com.apnamart.feature_home.data.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.mapper.toDomain
import com.apnamart.feature_auth.domain.model.RegisterResult
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import com.apnamart.feature_home.data.UserScoreApi
import com.apnamart.feature_home.data.mapper.toDomain
import com.apnamart.feature_home.domain.model.UserScore
import com.apnamart.feature_home.domain.repository.UserScoreRepository
import kotlinx.coroutines.flow.Flow

class UserScoreRepositoryImpl(
    private val api: UserScoreApi,
    private val networkMonitor: NetworkMonitor
) : UserScoreRepository {

    override suspend fun getUserScores(): Flow<UiState<List<UserScore>>>  {
        return safeFlowCall(networkMonitor) {
            api.getUserScores().map { it.toDomain() }
        }
    }
}