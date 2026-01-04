package com.apnamart.feature_auth.common

data class RegisterUiState(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
