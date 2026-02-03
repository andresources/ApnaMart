package com.apnamart.feature_cart.data.dto

import com.apnamart.feature_category.domain.model.CartItem

data class AddCartDto (
    val ordered_user: String = "T2,211",
    val ordered_user_phno: String = "8247599246",
    val transaction_id: String = "TXT_T2_211_12313",
    val items: List<CartItem>
)