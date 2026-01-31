package com.studyai.wellness.data.repository

import com.studyai.wellness.data.api.ApiService
import com.studyai.wellness.data.model.LoginResponseDto
import com.studyai.wellness.data.model.Resource
import com.studyai.wellness.data.model.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun login(email: String, password: String): Resource<LoginResponseDto> {
        return when (val result = apiService.login(email, password)) {
            is Resource.Success -> {
                dataStoreManager.saveToken(result.data.token)
                dataStoreManager.saveRefreshToken(result.data.refreshToken)
                result
            }
            is Resource.Error -> result
            else -> result
        }
    }

    suspend fun forgotPassword(email: String): Resource<Unit> {
        return apiService.forgotPassword(email)
    }

    fun getUserProfile(): Flow<Resource<UserDto>> {
        return apiService.getUserProfile()
    }

    suspend fun logout(): Resource<Unit> {
        val result = apiService.logout()
        dataStoreManager.clearTokens()
        return result
    }

    suspend fun isLoggedIn(): Boolean {
        return dataStoreManager.getToken() != null
    }

    fun getToken(): Flow<String?> {
        return dataStoreManager.token
    }
}
