package com.apnamart.feature_auth.common

data class ProfileUiState(
    val id: Int = 0,
    val user_name: String = "",
    val user_address: String = "",
    val user_phone_number: String = "",
    val user_email: String = "",
    val user_password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)
