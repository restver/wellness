package com.studyai.wellness.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.wellness.data.model.DashboardDto
import com.studyai.wellness.data.repository.DashboardRepository
import com.studyai.wellness.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            dashboardRepository.getDashboard().collect { result ->
                when (result) {
                    is com.studyai.wellness.data.model.Resource.Success -> {
                        _uiState.value = DashboardUiState.Success(result.data)
                    }
                    is com.studyai.wellness.data.model.Resource.Error -> {
                        _uiState.value = DashboardUiState.Error(
                            result.message ?: "Failed to load dashboard"
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = userRepository.logout()
            when (result) {
                is com.studyai.wellness.data.model.Resource.Success -> {
                    onSuccess()
                }
                else -> {}
            }
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val dashboard: DashboardDto) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
