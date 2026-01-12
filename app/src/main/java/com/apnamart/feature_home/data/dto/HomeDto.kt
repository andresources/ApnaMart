package com.apnamart.feature_home.data.dto

import com.google.gson.annotations.SerializedName

data class HomeDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumb_url")
    val thumbUrl: String,

    @SerializedName("original_url")
    val originalUrl: String,

    @SerializedName("item_id")
    val itemId: String,

    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("product_price")
    val productPrice: String,

    @SerializedName("offer_price")
    val offerPrice: String,

    @SerializedName("view_type")
    val viewType: String
)
