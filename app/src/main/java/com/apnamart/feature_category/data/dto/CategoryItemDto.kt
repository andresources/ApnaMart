package com.apnamart.feature_category.data.dto

data class CategoryItemDto(
    val id: Int,
    val title: String,
    val image_url: String,
    val category_id: Int,
    val original_price: Int,
    val offer_price: Int,
    val tower: String,
    val platno: String,
    val dname: String
)
