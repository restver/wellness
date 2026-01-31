package com.studyai.wellness.data.repository

import com.studyai.wellness.data.api.ApiService
import com.studyai.wellness.data.model.DashboardDto
import com.studyai.wellness.data.model.NotificationGroupDto
import com.studyai.wellness.data.model.Resource
import com.studyai.wellness.data.model.StatsDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getDashboard(): Flow<Resource<DashboardDto>> {
        return apiService.getDashboard()
    }

    fun getStats(timePeriod: String = "week"): Flow<Resource<StatsDto>> {
        return apiService.getStats(timePeriod)
    }

    fun getNotifications(): Flow<Resource<List<NotificationGroupDto>>> {
        return apiService.getNotifications()
    }

    suspend fun markNotificationAsRead(notificationId: String): Resource<Unit> {
        return apiService.markNotificationAsRead(notificationId)
    }

    suspend fun markAllNotificationsAsRead(): Resource<Unit> {
        return apiService.markAllNotificationsAsRead()
    }
}
