package com.apnamart.feature_auth.common


data class ForgotPasswordUiState(
    val email: String = "",
    val newpassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)