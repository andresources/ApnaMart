package com.apnamart.feature_category.data


import com.apnamart.feature_category.data.dto.CategoryDto
import com.apnamart.feature_category.data.dto.CategoryItemDto
import com.apnamart.feature_home.data.dto.UserScoreDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryAPI {
    @GET("ApartmentPrj/get_categories.php")
    suspend fun getCategories(): List<CategoryDto>

    @GET("ApartmentPrj/get_category_items.php")
    suspend fun getCategoryItems(@Query("category_id") categoryId: Int): List<CategoryItemDto>
}