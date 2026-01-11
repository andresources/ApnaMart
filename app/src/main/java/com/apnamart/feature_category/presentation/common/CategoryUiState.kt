package com.apnamart.feature_category.presentation.common

import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem

data class CategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val items: List<CategoryItem> = emptyList(),
    val selectedCategoryId: Int? = null,
    val error: String? = null
)
