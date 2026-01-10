package com.apnamart.feature_home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apnamart.core.common.UiState
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.core.presentation.BaseViewModel
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_auth.common.LoginUiState
import com.apnamart.feature_auth.domain.usecase.RegisterUserUseCase
import com.apnamart.feature_home.ScoreUiState
import com.apnamart.feature_home.domain.usecase.UserScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserScoreViewModel @Inject constructor(
    private val userScoreUseCase: UserScoreUseCase,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {
    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    fun loadUsersScore() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            userScoreUseCase().collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        Log.i("Dz99","Loding")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success -> {
                        Log.i("Dz99","Success")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false,
                                userScore = result.data
                            )
                        }
                    }

                    is UiState.Error -> {
                        Log.i("Dz99","Error : ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}