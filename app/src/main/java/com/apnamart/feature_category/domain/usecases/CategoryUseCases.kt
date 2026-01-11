package com.apnamart.feature_category.domain.usecases

import com.apnamart.core.common.UiState
import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem
import com.apnamart.feature_category.domain.repository.CategoryRepository
import com.apnamart.feature_home.domain.model.UserScore
import com.apnamart.feature_home.domain.repository.UserScoreRepository
import kotlinx.coroutines.flow.Flow


class CategoryUseCases(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<UiState<List<Category>>> {
        return repository.getCategories()
    }

    suspend operator fun invoke(category: Int): Flow<UiState<List<CategoryItem>>> {
        return repository.getCategoryItems(category)
    }
}