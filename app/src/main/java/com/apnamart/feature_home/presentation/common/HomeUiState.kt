package com.apnamart.feature_home.presentation.common

import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_home.domain.model.UserScore

data class HomeUiState(
    val homeData : List<HomeModel> = emptyList<HomeModel>(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)