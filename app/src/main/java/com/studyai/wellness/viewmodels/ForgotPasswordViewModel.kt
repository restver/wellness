package com.studyai.wellness.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.wellness.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun sendResetLink() {
        val email = _email.value

        if (!isValidEmail(email)) {
            _uiState.value = ForgotPasswordUiState.Error("Please enter a valid email")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            val result = userRepository.forgotPassword(email)
            when (result) {
                is com.studyai.wellness.data.model.Resource.Success -> {
                    _uiState.value = ForgotPasswordUiState.Success(email)
                }
                is com.studyai.wellness.data.model.Resource.Error -> {
                    _uiState.value = ForgotPasswordUiState.Error(
                        result.message ?: "Failed to send reset link"
                    )
                }
                else -> {}
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    data class Success(val email: String) : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
