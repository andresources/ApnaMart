package com.apnamart.feature_home.di

import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.repository.UserAuthRepositoryImpl
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import com.apnamart.feature_auth.domain.usecase.RegisterUserUseCase
import com.apnamart.feature_home.data.UserScoreApi
import com.apnamart.feature_home.data.repository.UserScoreRepositoryImpl
import com.apnamart.feature_home.domain.repository.UserScoreRepository
import com.apnamart.feature_home.domain.usecase.UserScoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UsersScroreModule {
    @Provides
    fun provideUserScoreApi(retrofit: Retrofit): UserScoreApi =
        retrofit.create(UserScoreApi::class.java)

    @Provides
    fun provideUserScoreRepository(
        api: UserScoreApi,
        networkMonitor: NetworkMonitor
    ): UserScoreRepository =
        UserScoreRepositoryImpl(api, networkMonitor)


    @Provides
    fun provideUserScoreUseCase(
        repository: UserScoreRepository
    ): UserScoreUseCase =
        UserScoreUseCase(repository)
}