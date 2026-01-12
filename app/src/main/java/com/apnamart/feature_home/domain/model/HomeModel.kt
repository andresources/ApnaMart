package com.apnamart.feature_home.domain.model

data class HomeModel(
    val id: Int,
    val title: String,
    val thumbUrl: String,
    val originalUrl: String,
    val itemId: Int,
    val categoryId: Int,
    val productPrice: Int,
    val offerPrice: Int,
    val viewType: String
) {
    val discount: Int
        get() = productPrice - offerPrice
}
