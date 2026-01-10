package com.apnamart.feature_home.domain.model

data class UserScore(
    val id: Int,
    val email: String,
    val model: String,
    val score: Int,
    val total: Int
)
