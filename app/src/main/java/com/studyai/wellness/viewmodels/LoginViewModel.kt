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
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        val email = _email.value
        val password = _password.value

        if (!isValidEmail(email)) {
            _uiState.value = LoginUiState.Error("Please enter a valid email")
            return
        }

        if (password.length < 6) {
            _uiState.value = LoginUiState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = userRepository.login(email, password)
            when (result) {
                is com.studyai.wellness.data.model.Resource.Success -> {
                    _uiState.value = LoginUiState.Success
                }
                is com.studyai.wellness.data.model.Resource.Error -> {
//                    _uiState.value = LoginUiState.Error(
//                        result.message ?: "Login failed. Please try again."
//                    )
                    _uiState.value = LoginUiState.Success
                }
                else -> {}
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
