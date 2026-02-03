package com.apnamart.feature_cart.data

import com.apnamart.feature_auth.data.remote.dto.ResponseDto
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_cart.data.dto.AddCartDto
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CheckOutApi {
    @POST("ApartmentPrj/add_cart_items.php")
    suspend fun addCartItems(
        @Body request: AddCartDto
    ): ResponseDto
}