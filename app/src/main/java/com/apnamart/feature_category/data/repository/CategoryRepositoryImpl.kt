package com.apnamart.feature_category.data.repository

import com.apnamart.core.common.UiState
import com.apnamart.core.common.safeFlowCall
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_category.data.CategoryAPI
import com.apnamart.feature_category.data.dto.CategoryDto
import com.apnamart.feature_category.data.dto.CategoryItemDto
import com.apnamart.feature_category.data.mapper.toDomain
import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem
import com.apnamart.feature_category.domain.repository.CategoryRepository
import com.apnamart.feature_home.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val api: CategoryAPI,
    private val networkMonitor: NetworkMonitor) : CategoryRepository
{
    override fun getCategories(): Flow<UiState<List<Category>>> {
        return safeFlowCall(networkMonitor) {
            api.getCategories().map { it.toDomain() }
        }
    }

    override fun getCategoryItems(categoryId: Int): Flow<UiState<List<CategoryItem>>> {
        return safeFlowCall(networkMonitor) {
            api.getCategoryItems(categoryId).map { it.toDomain() }
        }
    }
}