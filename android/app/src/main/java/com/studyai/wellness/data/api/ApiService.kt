package com.studyai.wellness.data.api

import com.studyai.wellness.data.model.DashboardDto
import com.studyai.wellness.data.model.DayProgressDto
import com.studyai.wellness.data.model.ForgotPasswordRequestDto
import com.studyai.wellness.data.model.HabitDto
import com.studyai.wellness.data.model.LoginRequestDto
import com.studyai.wellness.data.model.LoginResponseDto
import com.studyai.wellness.data.model.MetricDto
import com.studyai.wellness.data.model.NotificationGroupDto
import com.studyai.wellness.data.model.StatsDto
import com.studyai.wellness.data.model.UserDto
import com.studyai.wellness.data.model.WeeklyProgressDto
import com.studyai.wellness.data.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor() {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.studyai.com/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: ApiEndpoints = retrofit.create(ApiEndpoints::class.java)

    suspend fun login(email: String, password: String): Resource<LoginResponseDto> {
        return try {
            val response = api.login(LoginRequestDto(email, password))
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun forgotPassword(email: String): Resource<Unit> {
        return try {
            api.forgotPassword(ForgotPasswordRequestDto(email))
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getDashboard(): Flow<Resource<DashboardDto>> = flow {
        try {
            emit(Resource.Success(api.getDashboard()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    fun getStats(timePeriod: String): Flow<Resource<StatsDto>> = flow {
        try {
            emit(Resource.Success(api.getStats(timePeriod)))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    fun getNotifications(): Flow<Resource<List<NotificationGroupDto>>> = flow {
        try {
            emit(Resource.Success(api.getNotifications()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    suspend fun markNotificationAsRead(notificationId: String): Resource<Unit> {
        return try {
            api.markNotificationAsRead(notificationId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun markAllNotificationsAsRead(): Resource<Unit> {
        return try {
            api.markAllNotificationsAsRead()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getUserProfile(): Flow<Resource<UserDto>> = flow {
        try {
            emit(Resource.Success(api.getUserProfile()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    suspend fun logout(): Resource<Unit> {
        return try {
            api.logout()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
