package com.apnamart.feature_home.data

import com.apnamart.feature_home.data.dto.HomeDto
import com.apnamart.feature_home.data.dto.UserScoreDto
import com.apnamart.feature_home.domain.model.HomeModel
import retrofit2.http.GET

interface HomeApi {

    @GET("ApartmentPrj/get_home.php")
    suspend fun getHome(): List<HomeDto>

}