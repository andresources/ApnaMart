package com.apnamart.feature_home.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.feature_home.domain.model.HomeModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHomeDetails(): Flow<UiState<List<HomeModel>>>
}