package com.apnamart.feature_auth.common

data class LoginUiState(
    val emailOrPhone: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
