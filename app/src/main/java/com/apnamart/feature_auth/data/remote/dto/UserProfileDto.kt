package com.apnamart.feature_auth.data.remote.dto

data class UserProfileDto(
    val id: Int,
    val user_name: String,
    val user_address: String,
    val user_phone_number: Long,
    val user_email: String,
    val user_password: String
)
