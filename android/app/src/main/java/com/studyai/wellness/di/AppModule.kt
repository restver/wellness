package com.studyai.wellness.di

import com.studyai.wellness.data.api.ApiService
import com.studyai.wellness.data.repository.DashboardRepository
import com.studyai.wellness.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        dataStoreManager: com.studyai.wellness.data.repository.DataStoreManager
    ): UserRepository {
        return UserRepository(apiService, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(
        apiService: ApiService
    ): DashboardRepository {
        return DashboardRepository(apiService)
    }
}
