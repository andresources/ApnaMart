package com.apnamart.feature_home.domain.model

import com.apnamart.feature_category.domain.model.CategoryItem

data class HomeModel(
    val id: Int,
    val title: String,
    val thumbUrl: String,
    val originalUrl: String,
    val itemId: Int,
    val categoryId: Int,
    val productPrice: Int,
    val offerPrice: Int,
    val viewType: String,
    val tower: String,
    val platno: String,
    val dname: String

) {
    val discount: Int
        get() = productPrice - offerPrice
}

fun HomeModel.homeToCategoryItem() : CategoryItem{
    return CategoryItem(
        id = id,
        title = title,
        imageUrl = thumbUrl,          // or originalUrl if needed
        categoryId = categoryId,
        original_price = productPrice,
        offer_price = offerPrice,
        tower =tower,                   // default / API not provided
        platno = platno,
        dname = viewType              // mapping assumption
    )
}
