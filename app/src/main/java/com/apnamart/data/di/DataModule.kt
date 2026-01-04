package com.apnamart.data.di

import android.content.Context
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.data.remote.UserApi
import com.apnamart.data.remote.repository.UserRepositoryImpl
import com.apnamart.domain.repository.UserRepository
import com.apnamart.domain.usecase.RegisterUserUseCase
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
        api: UserApi
    ): UserRepository =
        UserRepositoryImpl(api)

    @Provides
    fun provideRegisterUserUseCase(
        repository: UserRepository
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
