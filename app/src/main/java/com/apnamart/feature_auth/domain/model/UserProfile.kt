package com.apnamart.feature_auth.domain.model

data class UserProfile(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val pwd: String
)
