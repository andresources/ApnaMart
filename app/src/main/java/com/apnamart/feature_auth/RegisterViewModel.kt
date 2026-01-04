package com.apnamart.feature_auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.core.common.UiState
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.domain.model.RegisterResult
import com.apnamart.domain.usecase.RegisterUserUseCase
import com.apnamart.feature_auth.common.LoginEvent
import com.apnamart.feature_auth.common.LoginUiState
import com.apnamart.feature_auth.common.RegisterEvent
import com.apnamart.feature_auth.common.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val authPreferences: AuthLocalDataSource,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiRState = MutableStateFlow(RegisterUiState())
    val uiRState = _uiRState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                _uiState.update {
                    it.copy(emailOrPhone = event.value, error = null)
                }

            is LoginEvent.PasswordChanged ->
                _uiState.update { it.copy(password = event.value, error = null) }

            LoginEvent.LoginClicked -> loginUser()
        }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged ->
                _uiRState.update { it.copy(name = event.value) }

            is RegisterEvent.AddressChanged ->
                _uiRState.update { it.copy(address = event.value) }

            is RegisterEvent.PhoneChanged ->
                _uiRState.update { it.copy(phone = event.value) }

            is RegisterEvent.EmailChanged ->
                _uiRState.update { it.copy(email = event.value) }

            is RegisterEvent.PasswordChanged ->
                _uiRState.update { it.copy(password = event.value) }

            RegisterEvent.Submit -> register()
        }
    }

    fun register() {
        val state = _uiRState.value

       /* if (state.name.isBlank() ||
            state.phone.length < 10 ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        ) {
            _uiState.update { it.copy(error = "Invalid input") }
            return
        }*/
        viewModelScope.launch {
            _uiRState.update { it.copy(isLoading = true, error = null) }
            registerUserUseCase(
                state.name, state.address, state.phone.toInt(), state.email, state.password
            ).collect { result ->
                when (result) {
                    is UiState.Loading ->{
                        _uiRState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success ->{
                        if(result.data.status){
                            authPreferences.saveUser(state.email)
                            _uiRState.update { it.copy(isLoading = false, isSuccess = true) }
                        }else{
                            _uiRState.update { it.copy(isLoading = false, isSuccess = false, error = result.data.message) }
                        }

                    }

                    is UiState.Error ->{
                        _uiRState.update { it.copy(isLoading = false,isSuccess = false, error = result.message) }
                    }
                }
            }
        }
    }

    fun loginUser() {
        val state = _uiState.value
        if (state.emailOrPhone.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(error = "All fields required") }
            return
        }
        viewModelScope.launch {
            registerUserUseCase(
                state.emailOrPhone, state.password
            ).collect { result ->
                when (result) {
                    is UiState.Loading ->{
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success ->{
                        if(result.data.status){
                            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                            authPreferences.saveUser(state.emailOrPhone)
                        }else{
                            _uiState.update { it.copy(isLoading = false, isSuccess = false, error = result.data.message) }
                        }

                    }

                    is UiState.Error ->{
                        _uiState.update { it.copy(isLoading = false,isSuccess = false, error = result.message) }
                    }
                }
            }
        }
    }
}
