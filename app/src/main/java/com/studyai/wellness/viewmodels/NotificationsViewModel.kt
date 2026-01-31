package com.studyai.wellness.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.wellness.data.model.NotificationGroupDto
import com.studyai.wellness.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationsUiState>(NotificationsUiState.Loading)
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _uiState.value = NotificationsUiState.Loading
            dashboardRepository.getNotifications().collect { result ->
                when (result) {
                    is com.studyai.wellness.data.model.Resource.Success -> {
                        _uiState.value = NotificationsUiState.Success(result.data)
                    }
                    is com.studyai.wellness.data.model.Resource.Error -> {
                        _uiState.value = NotificationsUiState.Error(
                            result.message ?: "Failed to load notifications"
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            dashboardRepository.markNotificationAsRead(notificationId)
            loadNotifications()
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            dashboardRepository.markAllNotificationsAsRead()
            loadNotifications()
        }
    }
}

sealed class NotificationsUiState {
    object Loading : NotificationsUiState()
    data class Success(val notifications: List<NotificationGroupDto>) : NotificationsUiState()
    data class Error(val message: String) : NotificationsUiState()
}
