package com.apnamart.feature_cart.di

import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_cart.data.CheckOutApi
import com.apnamart.feature_cart.data.repository.CheckOutRepositoryImpl
import com.apnamart.feature_cart.domain.repository.CheckOutRepository
import com.apnamart.feature_cart.domain.usecases.CheckOutUseCase
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
object CartDIModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): CheckOutApi =
        retrofit.create(CheckOutApi::class.java)

    @Provides
    fun provideCategoryRepository(
        api: CheckOutApi,
        networkMonitor: NetworkMonitor
    ): CheckOutRepository =
        CheckOutRepositoryImpl(api, networkMonitor)

    @Provides
    fun provideCheckOutUseCase(
        repository: CheckOutRepository
    ): CheckOutUseCase =
        CheckOutUseCase(repository)
}