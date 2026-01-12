package com.apnamart.feature_home.data

import com.apnamart.feature_home.data.dto.UserScoreDto
import retrofit2.http.GET

interface UserScoreApi {
    @GET("ApartmentPrj/getUsersScore.php")
     suspend fun getUserScores(): List<UserScoreDto>

}