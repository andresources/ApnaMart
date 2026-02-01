package com.apnamart.feature_category.domain.model

data class CartItem(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val imageUrl: String,
    val quantity: Int,
    val offer_price: Int,
    val original_price: Int,
    val tower: String,
    val platno: String,
    val dname: String,
)
