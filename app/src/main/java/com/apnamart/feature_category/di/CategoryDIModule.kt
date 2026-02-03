package com.apnamart.feature_category.di

import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_category.data.CategoryAPI
import com.apnamart.feature_category.data.repository.CategoryRepositoryImpl
import com.apnamart.feature_category.domain.repository.CategoryRepository
import com.apnamart.feature_category.domain.usecases.CategoryUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object CategoryDIModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): CategoryAPI =
        retrofit.create(CategoryAPI::class.java)

    @Provides
    fun provideCategoryRepository(
        api: CategoryAPI,
        networkMonitor: NetworkMonitor
    ): CategoryRepository =
        CategoryRepositoryImpl(api, networkMonitor)

    @Provides
    fun provideCategoryUseCase(
        repository: CategoryRepository
    ): CategoryUseCases =
        CategoryUseCases(repository)
}