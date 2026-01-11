package com.apnamart.feature_category.domain.repository

import com.apnamart.core.common.UiState
import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<UiState<List<Category>>>
    fun getCategoryItems(categoryId: Int): Flow<UiState<List<CategoryItem>>>
}