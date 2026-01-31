package com.studyai.wellness.data.api

import com.studyai.wellness.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequestDto): Unit

    @POST("auth/logout")
    suspend fun logout(): Unit

    @GET("dashboard")
    suspend fun getDashboard(): DashboardDto

    @GET("stats")
    suspend fun getStats(@Query("period") period: String): StatsDto

    @GET("notifications")
    suspend fun getNotifications(): List<NotificationGroupDto>

    @PUT("notifications/{id}/read")
    suspend fun markNotificationAsRead(@Path("id") notificationId: String): Unit

    @PUT("notifications/read-all")
    suspend fun markAllNotificationsAsRead(): Unit

    @GET("user/profile")
    suspend fun getUserProfile(): UserDto
}
