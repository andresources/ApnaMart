package com.apnamart.feature_auth.di

import android.content.Context
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_auth.data.remote.UserApi
import com.apnamart.feature_auth.data.remote.repository.UserAuthRepositoryImpl
import com.apnamart.feature_auth.domain.repository.UserAuthRepository
import com.apnamart.feature_auth.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    fun provideUserRepository(
        api: UserApi,
        networkMonitor: NetworkMonitor
    ): UserAuthRepository =
        UserAuthRepositoryImpl(api, networkMonitor)

    @Provides
    fun provideRegisterUserUseCase(
        repository: UserAuthRepository
    ): RegisterUserUseCase =
        RegisterUserUseCase(repository)

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        @ApplicationContext context: Context
    ): AuthLocalDataSource {
        return AuthLocalDataSource(context)
    }
}
