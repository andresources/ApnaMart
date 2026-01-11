package com.apnamart.feature_auth.data.remote

import com.apnamart.feature_auth.data.remote.dto.ResponseDto
import com.apnamart.feature_auth.data.remote.dto.UserProfileDto
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
    ): ResponseDto

    @FormUrlEncoded
    @POST("ApartmentPrj/user_login.php")
    suspend fun loginUser(
        @Field("login") login: String,
        @Field("user_password") password: String
    ): ResponseDto

    @FormUrlEncoded
    @POST("ApartmentPrj/forgotPassword.php")
    suspend fun forgotPassword(
        @Field("user_email") user_email: String,
        @Field("new_password") new_password: String
    ): ResponseDto

    @FormUrlEncoded
    @POST("ApartmentPrj/get_profile.php")
    suspend fun getUserProfile(
        @Field("user_email") user_email: String
    ): UserProfileDto

    // = Reddy& = Hyderabad& = 9999999999& = test11@gmail.com& = newpass123
    @FormUrlEncoded
    @POST("ApartmentPrj/update_user_profile.php")
    suspend fun updateUserProfile(
        @Field("user_name") user_name: String,
        @Field("user_address") user_address: String,
        @Field("user_phone_number") user_phone_number: String,
        @Field("user_email") user_email: String,
        @Field("user_password") user_password: String,
    ): ResponseDto


}
