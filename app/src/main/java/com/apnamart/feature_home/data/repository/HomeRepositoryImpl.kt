package com.apnamart.feature_home.data.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_home.data.HomeApi
import com.apnamart.feature_home.data.UserScoreApi
import com.apnamart.feature_home.data.mapper.toDomain
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_home.domain.model.UserScore
import com.apnamart.feature_home.domain.repository.HomeRepository
import com.apnamart.feature_home.domain.repository.UserScoreRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val api: HomeApi,
    private val networkMonitor: NetworkMonitor
) : HomeRepository {

    override suspend fun getHomeDetails(): Flow<UiState<List<HomeModel>>>  {
        return safeFlowCall(networkMonitor) {
            api.getHome().map { it.toDomain() }
        }
    }
}