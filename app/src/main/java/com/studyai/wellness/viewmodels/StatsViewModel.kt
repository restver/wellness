package com.studyai.wellness.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.wellness.data.model.StatsDto
import com.studyai.wellness.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatsUiState>(StatsUiState.Loading)
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    private val _selectedPeriod = MutableStateFlow("week")
    val selectedPeriod: StateFlow<String> = _selectedPeriod.asStateFlow()

    init {
        loadStats()
    }

    fun loadStats(period: String = _selectedPeriod.value) {
        viewModelScope.launch {
            _uiState.value = StatsUiState.Loading
            _selectedPeriod.value = period
            dashboardRepository.getStats(period).collect { result ->
                when (result) {
                    is com.studyai.wellness.data.model.Resource.Success -> {
                        _uiState.value = StatsUiState.Success(result.data)
                    }
                    is com.studyai.wellness.data.model.Resource.Error -> {
                        _uiState.value = StatsUiState.Error(
                            result.message ?: "Failed to load stats"
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

sealed class StatsUiState {
    object Loading : StatsUiState()
    data class Success(val stats: StatsDto) : StatsUiState()
    data class Error(val message: String) : StatsUiState()
}
