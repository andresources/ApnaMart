package com.apnamart.feature_home

import com.apnamart.feature_home.domain.model.UserScore

data class ScoreUiState(
    val userScore : List<UserScore> = emptyList<UserScore>(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
