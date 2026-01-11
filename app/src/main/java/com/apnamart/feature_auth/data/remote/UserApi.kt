package com.apnamart.feature_auth.data.remote

import com.apnamart.feature_auth.data.remote.dto.RegisterDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST("ApartmentPrj/user_registration.php")
    suspend fun registerUser(
        @Field("user_name") name: String,
        @Field("user_address") address: String,
        @Field("user_phone_number") phone: Int,
        @Field("user_email") email: String,
        @Field("user_password") password: String
    ): RegisterDto

    @FormUrlEncoded
    @POST("ApartmentPrj/user_login.php")
    suspend fun loginUser(
        @Field("login") login: String,
        @Field("user_password") password: String
    ): RegisterDto

    @FormUrlEncoded
    @POST("ApartmentPrj/forgotPassword.php")
    suspend fun forgotPassword(
        @Field("user_email") user_email: String,
        @Field("new_password") new_password: String
    ): RegisterDto
}
