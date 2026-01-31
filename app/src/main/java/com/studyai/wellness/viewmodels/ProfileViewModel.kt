package com.studyai.wellness.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.wellness.data.model.UserDto
import com.studyai.wellness.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            userRepository.getUserProfile().collect { result ->
                when (result) {
                    is com.studyai.wellness.data.model.Resource.Success -> {
                        _uiState.value = ProfileUiState.Success(result.data)
                    }
                    is com.studyai.wellness.data.model.Resource.Error -> {
                        _uiState.value = ProfileUiState.Error(
                            result.message ?: "Failed to load profile"
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

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: UserDto) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
