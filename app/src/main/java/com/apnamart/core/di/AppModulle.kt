package com.apnamart.core.di

import com.apnamart.core.data.repository.EnvironmentApiImpl
import com.apnamart.core.domain.repository.EnvironmentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModulle {
    @Provides
    @Singleton
    fun provideEnvironmentApi(): EnvironmentApi = EnvironmentApiImpl()
}