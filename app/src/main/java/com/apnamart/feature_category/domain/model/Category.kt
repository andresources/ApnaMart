package com.apnamart.feature_category.domain.model

data class Category(
    val id: Int,
    val name: String,
    val icon: String // local drawable OR mapped resource
)
